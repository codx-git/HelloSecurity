package example.hello_security.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import example.hello_security.mapper.SysUserMapper;
import example.hello_security.repository.SysUserRepository;
import example.hello_security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public List<SysUserRepository> selectAll() {
        return sysUserMapper.selectAll();
    }

    @Override
    public SysUserRepository loadUserByPhone(String phone) {
        QueryWrapper<SysUserRepository> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        return sysUserMapper.selectOne(queryWrapper);
    }

}
