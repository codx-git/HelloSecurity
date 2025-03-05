package example.hello_security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import example.hello_security.repository.SysUserRepository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUserRepository> {
    List<SysUserRepository> selectAll();
}
