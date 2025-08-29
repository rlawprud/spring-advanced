package org.example.expert.domain.todo.repository;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // N+1 -> 객체를 조회할 때,
    // JOIN 된 객체의 속성을 열람하기 위해 .getUser().getId() 와 같은 형태로 작업하는 것. (연관된 데이터를 개별적으로 가져옴)
    // 이렇게 조회할 경우, N번의 쿼리가 더 필요합니다.
    // 이를 해결하기 위해, 해당 레포지토리에서는 JOIN FETCH를 사용하여 해결하였습니다.
    // 하지만 이 경우 쿼리를 알지 못 할 경우 가독성이 좋지 않고, 번거롭습니다.
    // @EntityGraph를 통해 해결합니다.

    /*
    * 기존 코드입니다. Pagination을 위해 사용되는 메서드로, 해당 투두의 유저와 함께 수정일 기준으로 정렬하여 검색합니다.
    * @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    * Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);
    **/

    // attributePaths를 사용해 바로 fetch join 합니다.
    // user를 fetch join 하며, 메서드 명에 OrderByModifiedAtDesc가 들어가므로, 자동으로 정렬됩니다.
    @EntityGraph(attributePaths = {"user"})
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    /*
    * 기존 코드입니다. TodoId를 통해 검색하고, 유저를 검색 결과에 포함합니다.
    * @Query("SELECT t FROM Todo t " +
    *         "LEFT JOIN FETCH t.user " +
    *         "WHERE t.id = :todoId")
    * Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
    **/

    // 기존 코드와 같이 findByIdWithUser를 사용하려고 했을 때, 프로퍼티를 찾을 수 없는 오류가 발생합니다.
    // Service 에서 단건 조회를 위해 사용되는 메서드는 이 메서드 뿐이므로, 오버라이딩 합니다.
    // 또한, 원본 findById 에서 지정한 NonNullApi 규약 때문에 명시적으로 NonNull을 쓰지 않으면 경고가 뜹니다.
    // @NonNull 을 명시해 해결하였습니다.
    @Override
    @EntityGraph(attributePaths = {"user"})
    @NonNull
    Optional<Todo> findById(@NonNull Long id);

    int countById(Long todoId);
}
