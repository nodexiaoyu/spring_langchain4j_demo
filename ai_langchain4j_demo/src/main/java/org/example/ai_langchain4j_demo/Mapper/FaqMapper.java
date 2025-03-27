package org.example.ai_langchain4j_demo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.ai_langchain4j_demo.dto.Faq;
import java.util.List;

@Mapper
public interface FaqMapper extends BaseMapper<Faq> {
    @Select("SELECT * FROM faq WHERE MATCH(question_pattern) AGAINST(#{keyword} IN NATURAL LANGUAGE MODE)")
    List<Faq> searchByKeyword(String keyword);
    
    @Update("UPDATE faq SET call_count = call_count + 1 WHERE faq_id = #{faqId}")
    void incrementCallCount(Integer faqId);
}