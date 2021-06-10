package com.example.memcacheddemo;

import com.google.code.ssm.api.InvalidateAssignCache;
import com.google.code.ssm.api.ReadThroughAssignCache;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author $user$
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/06/03
 */
public class TaskRepositoryImpl implements CachedTaskRepository {
    @Autowired
    TaskRepository taskRepository;


/**  Check if value is in cache and if true return cached Value
 *  if not in cache ,execute function,return its value and store it in the cache
 */
    @Override
    @ReadThroughAssignCache(namespace = "Taskrepo",assignedKey = "all")
    public Iterable<Task> findAllCached() {
        return this.taskRepository.findAll();
    }

    @Override
    @InvalidateAssignCache(namespace="Taskrepo", assignedKey="all")
    public Task saveAndClearCache(Task t) {
        return this.taskRepository.save(t);
    }

    @Override
    @InvalidateAssignCache(namespace = "Taskrepo", assignedKey = "all")
    public void deleteByIdAndClearCache(Long id){
        this.taskRepository.deleteById(id);
    }
}
