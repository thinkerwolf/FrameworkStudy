package com.example.demo.mybatis;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.mybatis.dao.BlogMapper;
import com.example.demo.mybatis.domain.Blog;

public class MybatisStudy {
	public static void main(String[] args) throws Exception {
		String resource = "db/mybatis/mybatis.xml";
		InputStream is = Resources.getResourceAsStream(resource);
		SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = ssf.openSession();
		try {
			session.select("com.example.demo.mybatis.dao.BlogMapper.selectByPrimaryKey", 1, new ResultHandler<Blog>() {
				@Override
				public void handleResult(ResultContext<? extends Blog> resultContext) {
					System.out.println(resultContext.getResultObject());
				}
			});

			BlogMapper blogMapper = session.getMapper(BlogMapper.class);
			Blog r = blogMapper.selectOneBlog(2);
			System.out.println(r.getContent());
			session.commit();
		} finally {
			session.close();
		}

	}

}
