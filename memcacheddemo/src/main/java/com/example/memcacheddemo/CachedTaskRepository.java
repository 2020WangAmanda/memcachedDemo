package com.example.memcacheddemo;

/**
 * @author $user$
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/06/03
 */
public interface CachedTaskRepository {
    public Iterable<Task> findAllCached();
    public Task saveAndClearCache(Task t);
    public void deleteByIdAndClearCache(Long id);
}
