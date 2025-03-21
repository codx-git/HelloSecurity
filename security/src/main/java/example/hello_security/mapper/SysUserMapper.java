package example.hello_security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import example.hello_security.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser selectByUsername(String username);
    List<SysUser> selectAll();
    void updateLastLoginTime(String username);
}
