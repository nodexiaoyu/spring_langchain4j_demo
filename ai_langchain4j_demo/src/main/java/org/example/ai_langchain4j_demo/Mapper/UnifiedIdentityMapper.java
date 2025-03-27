package org.example.ai_langchain4j_demo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.ai_langchain4j_demo.dto.UnifiedIdentity;
import java.util.List;

@Mapper
public interface UnifiedIdentityMapper extends BaseMapper<UnifiedIdentity> {
    @Select("SELECT * FROM unified_identity WHERE identity_type = #{type}")
    List<UnifiedIdentity> findByIdentityType(UnifiedIdentity.IdentityType type);
}