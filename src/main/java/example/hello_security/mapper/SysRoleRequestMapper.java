package example.hello_security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import example.hello_security.entity.SysRoleRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleRequestMapper extends BaseMapper<SysRoleRequest> {
    List<SysRoleRequest> selectAll();
}
