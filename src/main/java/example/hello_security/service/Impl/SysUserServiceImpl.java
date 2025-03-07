package example.hello_security.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import example.hello_security.mapper.SysUserMapper;
import example.hello_security.repository.SysUserRepository;
import example.hello_security.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    private final Logger logger = LoggerFactory.getLogger(SysUserService.class);
    @Override
    public List<SysUserRepository> selectAll() {
        logger.info("This is a logger test {}:{}",1,2);
        return sysUserMapper.selectAll();
    }

    @Override
    public SysUserRepository loadUserByPhone(String phone) {
        QueryWrapper<SysUserRepository> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        return sysUserMapper.selectOne(queryWrapper);
    }

}
