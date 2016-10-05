package com.k.kpp.business.freemarker.article;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.k.kpp.business.freemarker.constant.ConstStaticHtmlDir;
import com.k.kpp.business.freemarker.model.Article;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/news")
public class NewsController {

	@Resource
	private FreeMarkerConfigurer freeMarkerConfigurer;

	/**
	 * 列表页
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/newsList")
	public String getNewsListPage(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException {
		// 测试数据
		List<Article> list = getTestData();

		// 模板需要数据
		Map<String, Object> articleData = new HashMap<>();
		articleData.put("articles", list);

		// 模板生成静态页目录
		String basePath = request.getSession().getServletContext().getRealPath("/");
		String htmlDir = basePath + ConstStaticHtmlDir.NEWS_LIST;
		String htmlName = "/newsList.html";
		String htmlPath = htmlDir + htmlName;
		
		// 生成 静态新闻列表页
		try {
			//模板
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate("newsList.ftl");
			//模板生成 HTML 内容
			String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, articleData);
			//生成静态页 列表页
			//this.createHtml(htmlText, htmlPath, true);
			string2File(htmlText,htmlPath);
		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 生成静态内容页
		getNewsContentPage(request, response);
		// 跳转至静态页
		return "redirect:../static/html/list/newsList.html";
	}

	/**
	 * 内容页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	private void getNewsContentPage(HttpServletRequest request, HttpServletResponse response) {
		// 测试数据
		List<Article> list = getTestData();
		
		// 模板需要数据
		Map<String, Object> articleData = new HashMap<>();
		
		// 模板生成静态页目录
		String htmlDir = request.getSession().getServletContext().getRealPath("/") + ConstStaticHtmlDir.NEWS_CONTENT;

		//循环生成列表页中的内容页
		for (Article article : list) {
			//内容页数据
			articleData.put("article", article);
			//内容页名称
			String htmlName = "/"+article.getId() + ".html";
			//内容页路径
			String htmlPath = htmlDir + htmlName;

			// 获得 模板
			Template template = null;
			// 获得 模板转换的 html字符串
			String htmlText = null;
			try {
				template = freeMarkerConfigurer.getConfiguration().getTemplate("newsContent.ftl");
				htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, articleData);
			} catch (IOException | TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			 //createHtml(htmlText, htmlPath, true);
			 string2File(htmlText, htmlPath);
		}
	}

	/**
	 * 测试数据
	 * 
	 * @return
	 */
	private List<Article> getTestData() {
		List<Article> articles = new ArrayList<Article>();
		articles.add(new Article(20160701, "不明真相的美国人被UFO惊呆了 其实是长征7号",
				"据美国《洛杉矶时报》报道，当地时间周三晚(北京时间周四)，在美国中西部的犹他州、内华达州、加利福利亚州，数千人被划过夜空的神秘火球吓到"));
		articles.add(new Article(20160702, "法国巴黎圣母院为教堂恐袭案遇害神父举行大弥撒", "而据美国战略司令部证实，其实这是中国长征七号火箭重新进入大气层，刚好经过加利福利亚附近。"));
		articles.add(new Article(20160703, "日东京知事候选人小池百合子回击石原：浓妆可以", "然而昨晚的美国人民可不明真相，有些人甚至怀疑这些火球是飞机解体，还有些人猜测是流星雨。"));
		articles.add(new Article(20160704, "日资慰安妇基金在首尔成立 韩国示威者闯入抗议",
				"美国战略司令部发言人表示，到目前为止还没有任何受损报告，他说类似物体通常在大气中就会消失，这也解释了为何出现一道道光痕，这一切都并未造成什么威胁。"));
		articles.add(new Article(20160705, "中日关系正处十字路口日应寻求减少与华冲突",
				"中国长征七号火箭6月25日在海南文昌航天发射中心首次发射，并成功升空进入轨道。有学者指出长征七号第二级火箭一直在地球低轨运行，一个月后重新进入大气层。"));
		return articles;
	}

	//path 不存在则失败
	@SuppressWarnings("unused")
	private boolean createHtml(String str, String filePath, boolean flag) {
		boolean r = false;
		File writeFile = new File(filePath);
		try {
			if (writeFile.exists()) {
				if (flag) {
					writeFile.delete();
					writeFile.createNewFile();
				} else {
					writeFile.createNewFile();
				}
			} else {
				writeFile.delete();
				writeFile.createNewFile();
			}
			OutputStream os = new FileOutputStream(writeFile, true);
			os.write(str.getBytes("utf-8"));
			os.close();
		} catch (IOException e) {
			System.err.println("create html error:" + filePath);
		}
		return r;
	}

	//path 不存在则创建
	private boolean string2File(String text, String htmlPath) {
		File distFile = new File(htmlPath);
		if (!distFile.getParentFile().exists())
			distFile.getParentFile().mkdirs();
		BufferedReader br = null;
		BufferedWriter bw = null;
		boolean r = true;
		try {
			br = new BufferedReader(new StringReader(text));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(distFile),"UTF-8"));//解决编码
			char buf[] = new char[1024 * 64]; // 字符缓冲区
			int len;
			while ((len = br.read(buf)) != -1) {
				bw.write(buf, 0, len);
			}
			bw.flush();
			br.close();
			bw.close();
		} catch (IOException e) {
			r = false;
			System.err.println("create file error!");
		}
		return r;
	}

}
