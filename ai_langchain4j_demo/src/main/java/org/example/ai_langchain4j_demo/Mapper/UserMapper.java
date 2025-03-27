package org.example.ai_langchain4j_demo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.ai_langchain4j_demo.dto.User;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE department = #{department}")
    List<User> findByDepartment(String department);
    
    @Select("SELECT * FROM user WHERE student_no = #{studentNo}")
    User findByStudentNo(String studentNo);
}