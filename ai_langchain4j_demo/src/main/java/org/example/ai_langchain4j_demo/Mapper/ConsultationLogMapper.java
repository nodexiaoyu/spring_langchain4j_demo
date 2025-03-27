package org.example.ai_langchain4j_demo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.ai_langchain4j_demo.dto.ConsultationLog;
import java.util.List;

@Mapper
public interface ConsultationLogMapper extends BaseMapper<ConsultationLog> {
    @Select("SELECT * FROM consultation_log WHERE user_id = #{userId} ORDER BY consult_time DESC")
    List<ConsultationLog> findByUserId(Integer userId);
    
    @Select("SELECT * FROM consultation_log WHERE session_id = #{sessionId} ORDER BY consult_time")
    List<ConsultationLog> findBySessionId(String sessionId);
}