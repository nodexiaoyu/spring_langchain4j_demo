package org.example.ai_langchain4j_demo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.ai_langchain4j_demo.dto.OperationLog;

import java.util.List;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
    
    @Select("SELECT * FROM operation_log WHERE trace_id = #{traceId} ORDER BY operation_time")
    List<OperationLog> findByTraceId(String traceId);
    
    @Select("SELECT * FROM operation_log WHERE user_id = #{userId} ORDER BY operation_time DESC LIMIT #{limit}")
    List<OperationLog> findRecentByUserId(Integer userId, Integer limit);
    
    @Select("SELECT * FROM operation_log WHERE module = #{module} ORDER BY operation_time DESC LIMIT #{limit}")
    List<OperationLog> findRecentByModule(String module, Integer limit);
}