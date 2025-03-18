package example.hello_security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import example.hello_security.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> roleByUsername(String username);
}
