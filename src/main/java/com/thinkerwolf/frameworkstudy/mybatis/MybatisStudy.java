package com.thinkerwolf.frameworkstudy.mybatis;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.thinkerwolf.frameworkstudy.mybatis.dao.BlogMapper;
import com.thinkerwolf.frameworkstudy.mybatis.domain.Blog;

public class MybatisStudy {
	public static void main(String[] args) throws Exception {
		String resource = "db/mybatis/mybatis.xml";
		
		InputStream is = Resources.getResourceAsStream(resource);
		SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(is);
		
		try (SqlSession session = ssf.openSession()) {
			session.select("com.thinkerwolf.frameworkstudy.mybatis.dao.BlogMapper.selectByPrimaryKey", 1, new ResultHandler<Blog>() {
				@Override
				public void handleResult(ResultContext<? extends Blog> resultContext) {
					System.out.println(resultContext.getResultObject());
				}
			});
			BlogMapper blogMapper = session.getMapper(BlogMapper.class);
			Blog r = blogMapper.selectOneBlog(1);
			System.out.println(r.getContent());
			session.commit();
		}

	}

}
