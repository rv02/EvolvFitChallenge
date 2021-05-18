package com.rv02.evolvFit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentChildCommentRepository extends
        JpaRepository<ParentChildComment, Integer> {

    @Query(
            value = "select PARENT_COMMENT_ID from PARENT_CHILD_COMMENT where CHILD_COMMENT_ID = ?1",
            nativeQuery = true
    )
    List<Integer> findAllParents(int id);

}
