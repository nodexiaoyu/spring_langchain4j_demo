package org.example.ai_langchain4j_demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.User;
import org.example.ai_langchain4j_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Tag(name = "用户管理", description = "用户信息的增删改查接口")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    
    @Operation(summary = "添加新用户", description = "添加一个新的用户到系统")
    @PostMapping("/add")
    public CompletableFuture<ApiResult<User>> addUser(
            @Parameter(description = "用户信息，包含学号、姓名等") @RequestBody User user) {
        return userService.addUserAsync(user);
    }

    @Operation(summary = "更新用户信息", description = "根据用户ID更新用户信息")
    @PutMapping("/update")
    public CompletableFuture<ApiResult<User>> updateUser(
            @Parameter(description = "更新后的用户信息") @RequestBody User user) {
        return userService.updateUserAsync(user);
    }

    @Operation(summary = "根据ID查询用户", description = "通过用户ID查询用户详细信息")
    @GetMapping("/getById/{userId}")
    public CompletableFuture<ApiResult<User>> getUserById(
            @Parameter(description = "用户ID") @PathVariable Integer userId) {
        return userService.getUserAsync(userId);
    }

    @Operation(summary = "根据学号查询用户", description = "通过学号查询用户详细信息")
    @GetMapping("/getByStudentNo/{studentNo}")
    public CompletableFuture<ApiResult<User>> getUserByStudentNo(
            @Parameter(description = "学号") @PathVariable String studentNo) {
        return userService.getUserByStudentNoAsync(studentNo);
    }
}
