

package org.example.ai_langchain4j_demo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.ai_langchain4j_demo.dto.PolicyKnowledge;
import org.example.ai_langchain4j_demo.entity.Policy;

import java.util.List;

@Mapper
public interface PolicyMapper extends BaseMapper<Policy> {
    
    // 根据政策类型查询政策
    @Select("SELECT * FROM policy_knowledge WHERE knowledge_type = #{type} AND status = '生效'")
    List<PolicyKnowledge> findByType(PolicyKnowledge.KnowledgeType type);
    
    // 查询所有生效的政策
    @Select("SELECT * FROM policy_knowledge WHERE status = '生效'")
    List<PolicyKnowledge> findActivePolicy();
    
    // 根据关键词搜索政策
    @Select("SELECT * FROM policy_knowledge WHERE (knowledge_title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%')) AND status = '生效'")
    List<PolicyKnowledge> searchByKeyword(String keyword);
}
