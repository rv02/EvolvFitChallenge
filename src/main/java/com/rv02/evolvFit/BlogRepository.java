package com.rv02.evolvFit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends
        JpaRepository<Blog, Integer> {
}
