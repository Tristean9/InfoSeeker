/*
author: 请问
2024/1/17  20:19

*/
package com.zth.infoseeker.service.impl;

import com.zth.infoseeker.model.RawDocument;
import com.zth.infoseeker.repository.RawDocumentRepository;
import com.zth.infoseeker.service.interfaces.ICrawlerService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.SeparatorUI;
import java.io.IOException;
import java.util.*;

@Service
public class CrawlerServiceImpl implements ICrawlerService {

    @Autowired
    private RawDocumentRepository rawDocumentRepository;

    @Override
    public List<String> crawlXml() throws IOException, InterruptedException {
        List<String> savedUrls = new ArrayList<>();
        String[] monthlst = {"12", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        String yearStr = String.valueOf(year);

        for (int m = 0; m <= 0; m++) {
            String url;
            if (month != 0) {
                url = "https://www.govinfo.gov/bulkdata/FR/" + yearStr + "/" + monthlst[month + 1 - m];
            } else {
                year = year - m;
                yearStr = String.valueOf(year);
                url = "https://www.govinfo.gov/bulkdata/FR/" + yearStr + "/" + monthlst[month + 1 - m];
            }

            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("link"); // 修改这里的选择器

            for (Element link : links) {
                String linkText = link.text(); // 修改这里获取链接文本
                System.out.println("linkText: "+ linkText);
                if (linkText.endsWith(".xml")) { // 检查链接文本是否以.xml结尾
                    try {
                        if (!rawDocumentRepository.existsByUrl(linkText)) {
                            System.out.println("yes");
                            Connection.Response response = Jsoup.connect(linkText)
                                    .maxBodySize(5000000)
                                    .method(Method.GET)
                                    .ignoreContentType(true)
                                    .execute();

                            // 确保响应体不为空
                            byte[] bodyAsBytes = response.bodyAsBytes();
                            if (bodyAsBytes.length > 0) {
                                RawDocument rawDocument = new RawDocument();
                                rawDocument.setUrl(linkText);
                                rawDocument.setXmlContent(new String(bodyAsBytes)); // 假设您存储的是字符串，并且编码是UTF-8

                                // 在保存之前打印对象的状态，调试用
                                System.out.println("Saving RawDocument: " + rawDocument);

                                rawDocumentRepository.save(rawDocument); // 存储到数据库
                                savedUrls.add(linkText);
                            } else {
                                System.err.println("The response body is empty for URL: " + linkText);
                            }
                        }else {
                            System.out.println("该xml已存在");
                        }
                    } catch (IOException e) {
                        System.err.println("An IOException occurred while connecting to URL: " + linkText);
                        e.printStackTrace(); // 打印堆栈跟踪信息以供调试
                    } catch (Exception e) {
                        System.err.println("An unexpected exception occurred: " + e.getMessage());
                        e.printStackTrace(); // 打印堆栈跟踪信息以供调试
                    } finally {
                        // 使用随机的延迟以模拟人类浏览行为并避免请求速度过快
                        Random random = new Random();
                        Thread.sleep(Math.abs(random.nextInt(10)));
                    }
                }

            }
        }
        return savedUrls; // 返回已存储文档的URL列表
    }
    @Override
     public List<String> crawlXmlForToday() throws IOException, InterruptedException {
        List<String> savedUrls = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        // 获取当前的年、月、日
        String yearStr = String.valueOf(calendar.get(Calendar.YEAR));
        String monthStr = String.format("%02d", calendar.get(Calendar.MONTH) + 1); // Calendar.MONTH是从0开始的
        String dayStr = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH) - 1);

        // 构建当月的URL
        String url = "https://www.govinfo.gov/bulkdata/FR/" + yearStr + "/" + monthStr;

        System.out.println(url);
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("link"); // 修改这里的选择器

        for (Element link : links) {
            String linkText = link.text(); // 修改这里获取链接文本
            if (linkText.contains(yearStr + '-' + monthStr + '-' + dayStr) && linkText.endsWith(".xml")) { // 检查链接是否包含当前日期并以.xml结尾
                 // 构建完整的URL
                 //linkText = url + "/" + linkText;
                System.out.println("linkText: "+ linkText);
                 try {
                        if (!rawDocumentRepository.existsByUrl(linkText)) {
                            System.out.println("yes");
                            Connection.Response response = Jsoup.connect(linkText)
                                    .maxBodySize(5000000)
                                    .method(Method.GET)
                                    .ignoreContentType(true)
                                    .execute();

                            // 确保响应体不为空
                            byte[] bodyAsBytes = response.bodyAsBytes();
                            if (bodyAsBytes.length > 0) {
                                RawDocument rawDocument = new RawDocument();
                                rawDocument.setUrl(linkText);
                                rawDocument.setXmlContent(new String(bodyAsBytes)); // 假设您存储的是字符串，并且编码是UTF-8

                                // 在保存之前打印对象的状态，调试用
                                // System.out.println("Saving RawDocument: " + rawDocument);

                                rawDocumentRepository.save(rawDocument); // 存储到数据库
                                savedUrls.add(linkText);
                            } else {
                                System.err.println("The response body is empty for URL: " + linkText);
                            }
                        }else {
                            System.out.println("该xml已存在");
                        }
                    } catch (IOException e) {
                        System.err.println("An IOException occurred while connecting to URL: " + linkText);
                        e.printStackTrace(); // 打印堆栈跟踪信息以供调试
                    } catch (Exception e) {
                        System.err.println("An unexpected exception occurred: " + e.getMessage());
                        e.printStackTrace(); // 打印堆栈跟踪信息以供调试
                    } finally {
                        // 使用随机的延迟以模拟人类浏览行为并避免请求速度过快
                        Random random = new Random();
                        Thread.sleep(Math.abs(random.nextInt(10)));
                    }
            }
        }

        // 使用随机的延迟模拟人类行为
        Random random = new Random();
        Thread.sleep(random.nextInt(10));

        return savedUrls; // 返回已存储文档的URL列表
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        CrawlerServiceImpl crawlerService = new CrawlerServiceImpl();

        List<String> todayUrls = crawlerService.crawlXmlForToday();
        System.out.println(todayUrls.toString());
    }
}

