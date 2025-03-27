package org.example.ai_langchain4j_demo.service;

import org.example.ai_langchain4j_demo.Mapper.UserMapper;
import org.example.ai_langchain4j_demo.dto.User;
import org.example.ai_langchain4j_demo.dto.ApiResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

/**
 * UserService 类提供了用户相关的异步操作服务。
 * 使用 CompletableFuture 和虚拟线程来提高响应速度。
 */
@Service
public class UserService {
    /**
     * 自动注入 UserMapper，用于数据库操作。
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 自动注入虚拟线程执行器，用于异步任务处理。
     * 使用 @Qualifier 指定具体的线程池名称。
     */
    @Autowired
    @Qualifier("virtualThreadExecutor")
    private ThreadPoolTaskExecutor virtualThreadExecutor;

    /**
     * 异步添加用户。
     * 使用虚拟线程执行数据库插入操作，并返回 CompletableFuture。
     *
     * @param user 要添加的用户对象
     * @return CompletableFuture 包含操作结果的 ApiResult
     */
    public CompletableFuture<ApiResult<User>> addUserAsync(User user) {
        return CompletableFuture.supplyAsync(() -> {
            userMapper.insert(user); // 插入用户到数据库
            return ApiResult.success(user); // 返回成功的 ApiResult
        }, virtualThreadExecutor); // 使用虚拟线程执行器
    }

    /**
     * 异步更新用户。
     * 使用虚拟线程执行数据库更新操作，并返回 CompletableFuture。
     *
     * @param user 要更新的用户对象
     * @return CompletableFuture 包含操作结果的 ApiResult
     */
    public CompletableFuture<ApiResult<User>> updateUserAsync(User user) {
        return CompletableFuture.supplyAsync(() -> {
            userMapper.updateById(user); // 更新用户信息
            return ApiResult.success(user); // 返回成功的 ApiResult
        }, virtualThreadExecutor); // 使用虚拟线程执行器
    }

    /**
     * 异步获取用户信息（通过用户ID）。
     * 使用虚拟线程执行数据库查询操作，并返回 CompletableFuture。
     *
     * @param userId 用户ID
     * @return CompletableFuture 包含操作结果的 ApiResult
     */
    public CompletableFuture<ApiResult<User>> getUserAsync(Integer userId) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userMapper.selectById(userId); // 根据用户ID查询用户
            return ApiResult.success(user); // 返回成功的 ApiResult
        }, virtualThreadExecutor); // 使用虚拟线程执行器
    }

    /**
     * 异步获取用户信息（通过学号）。
     * 使用虚拟线程执行数据库查询操作，并返回 CompletableFuture。
     *
     * @param studentNo 学号
     * @return CompletableFuture 包含操作结果的 ApiResult
     */
    public CompletableFuture<ApiResult<User>> getUserByStudentNoAsync(String studentNo) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userMapper.findByStudentNo(studentNo); // 根据学号查询用户
            return ApiResult.success(user); // 返回成功的 ApiResult
        }, virtualThreadExecutor); // 使用虚拟线程执行器
    }
}
