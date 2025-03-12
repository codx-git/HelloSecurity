package example.hello_security;

import example.hello_security.entity.SysUser;
import example.hello_security.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HelloSecurityApplicationTests {

	@Test
	void contextLoads() {
		JwtUtils jwtUtils = new JwtUtils();
		// 创建一个 SysUser 实例
		SysUser sysUser = new SysUser();
		sysUser.setUsername("ssss");
		// 生成 Token
		String token = jwtUtils.generateToken(sysUser);
		assertNotNull(token, "Generated token should not be null");
		// 验证 Token
		boolean isValid = jwtUtils.validateToken(token);
		assertTrue(isValid, "Generated token should be valid");
	}

}
