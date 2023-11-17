package com.theZ.dotoring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"jwt.secretKey=your-test-secret-key-dotoring-12345-9393",
		"jwt.accessTokenExp=86400000",
		"jwt.refreshTokenExp=432000000",
		"cloud.aws.credentials.accessKey=your-test-access-key-dotoring-12345-9393",
		"cloud.aws.credentials.secretKey=your-test-secret-key-dotoring-12345-9393",
		"cloud.aws.s3.bucket=your-test-bucket-dotoring-12345-9393",
		"profileUrl=https://your-test-bucket-dotoring-12345-9393.s3.ap-northeast-2.amazonaws.com/"
})
class DotoringApplicationTests {

	@Test
	void contextLoads() {
	}

}
