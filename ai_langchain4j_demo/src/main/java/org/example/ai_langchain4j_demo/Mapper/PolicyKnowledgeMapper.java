package org.example.ai_langchain4j_demo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.ai_langchain4j_demo.dto.PolicyKnowledge;
import java.util.List;

@Mapper
public interface PolicyKnowledgeMapper extends BaseMapper<PolicyKnowledge> {
    
    @Select("SELECT * FROM policy_knowledge WHERE knowledge_type = #{type} AND status = '生效'")
    List<PolicyKnowledge> findByType(String type);
    
    @Select("SELECT * FROM policy_knowledge WHERE knowledge_title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%') AND status = '生效'")
    List<PolicyKnowledge> searchByKeyword(String keyword);
}