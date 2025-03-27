package org.example.ai_langchain4j_demo.tools;

import java.util.List;

import org.example.ai_langchain4j_demo.Mapper.PolicyMapper;
import org.example.ai_langchain4j_demo.dto.PolicyKnowledge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.langchain4j.agent.tool.Tool;

@Component
public class PolicyTool {
    @Autowired
    private PolicyMapper policyMapper;

    @Tool("查询特定类型的政策")
    public String queryPolicyByType(PolicyKnowledge.KnowledgeType policyType){
        List<PolicyKnowledge> policies = policyMapper.findByType(policyType);
        if (policies.isEmpty()) {
            return "没有找到相关政策。";    
        }
        StringBuilder result = new StringBuilder();
        result.append("找到以下政策：\n\n");
        for(PolicyKnowledge policy : policies){
            result.append("- ").append(policy.getKnowledgeTitle()).append("\n");
            result.append("  ").append(policy.getContent()).append("\n\n");
        }
        return result.toString();
    }


    @Tool("根据关键词查询政策")
    public String searchPolicyByKeyword(String keyword){
        List<PolicyKnowledge> policies = policyMapper.searchByKeyword(keyword);
        if (policies.isEmpty()) {
            return "没有找到关键词"+keyword+"相关的政策。";
        }
        StringBuilder result = new StringBuilder();
        result.append("找到以下包含 '").append(keyword).append("' 的政策：\n\n");
        for (PolicyKnowledge policy : policies) {
            result.append("- ").append(policy.getKnowledgeTitle()).append("\n");
            result.append("  ").append(policy.getContent()).append("\n\n");
        }
        
        return result.toString();
    }   
}
