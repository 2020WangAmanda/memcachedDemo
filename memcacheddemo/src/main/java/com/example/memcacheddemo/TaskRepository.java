package com.example.memcacheddemo;

/**
 * @author $user$
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/05/31
 */

import org.springframework.data.repository.CrudRepository;

// spring memcached doc
public interface TaskRepository extends CrudRepository<Task, Long>, CachedTaskRepository {}
