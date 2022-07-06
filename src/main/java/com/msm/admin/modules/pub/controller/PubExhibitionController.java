package com.msm.admin.modules.pub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.View;
import com.msm.admin.modules.info.entity.Exhibition;
import com.msm.admin.modules.pub.service.PubExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quavario@gmail.com
 * @date 2019/12/17 10:13
 */
@RestController
@RequestMapping("/v1/exhibitions")
public class PubExhibitionController {

    @Autowired
    private PubExhibitionService exhibitionService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage pageSearch(Page<Exhibition> page, Exhibition exhibition) {
        IPage iPage = exhibitionService.pageSearch(page, exhibition);
        return iPage;
    }

    /**
     * 全景列表查询
     */
    @GetMapping("/panos")
    public IPage panoPageSearch(Page<Exhibition> page, Exhibition exhibition) {
        IPage iPage = exhibitionService.panoPageSearch(page, exhibition);
        return iPage;
    }



    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @View(type = "exhibition")
    public Exhibition queryById(@PathVariable String id) {
        Exhibition exhibition = exhibitionService.getById(id);
        return exhibition;
    }

    /**
     * 临展与交流站
     * @param page
     * @param exhibition
     * @return
     */
    @GetMapping("/temporary")
    public IPage temporaryExhibitionList(Page<Exhibition> page,Exhibition exhibition) {
        return exhibitionService.temporaryExhibitionList(page, exhibition);
    }

    @GetMapping("/geming")
    public List<Map<String, String>> geming() {
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            HashMap<String, String> exhibition = new HashMap<>();
            arrayList.add(exhibition);
        }
        arrayList.get(1).put("depId", "6b08e1e1983db0ddc06b1534a28e46e8");
        arrayList.get(1).put("title", "/images/gmwwPage/new/name1.png");
        arrayList.get(1).put("name", "八路军一二九师纪念馆");
        arrayList.get(1).put("addr", "/images/gmwwPage/new/address1.png");
        arrayList.get(1).put("pano", "http://www.hb-museum.com/data/zip/8804827a-0558-456c-8ea9-c1bcfd382f37/index.html");
        arrayList.get(1).put("bgImg", "/images/gmwwPage/new/pic1.png");
        arrayList.get(1).put("banner", "/images/gmwwPage/new/banner1.png");
        arrayList.get(1).put("intro", "位于河北省涉县赤岸村，是国内唯一一处全面、详实记录抗战时期八路军一二九师历史的纪念馆。");
        arrayList.get(1).put("info", "<p>八路军一二九师纪念馆位于河北省邯郸市涉县赤岸村，于1979年正式对外开放，占地面积5.6平方公里，是国内唯一一处全面、详实记录抗战时期八路军一二九师历史的纪念馆。</p>" +
                "<p>以《我们在太行山上》为主题的陈列展，由序厅、五个展室和半景画馆组成。围绕主题，展厅共分为四大部分，即“挥师入太行”、“太行鏖战”、“太行情深”、“雄师出太行”，集中反映了八路军一二九师转战太行、创建晋冀鲁豫抗日根据地、千里跃进大别山，为中华民族的解放事业作出历史性贡献的光辉历程。</p>" +
                "<p>馆里珍藏着500余件一二九师八路军战士用过的刀枪弹药、娱乐器具等战斗和生活用品，以及缴获日军的指挥刀、汽车等物品。</p>" +
                "<p>该馆现为全国重点文物保护单位、全国首批百个爱国主义教育示范基地之一、全国红色旅游经典景区、国家AAAA级旅游景区、国家级风景名胜区、国家国防教育示范基地、国家二级博物馆、国家级抗战纪念设施遗址、全国中小学生研学实践教育基地、全国十大最美研学旅行基地、全国关心下一代党史国史教育基地、全国巾帼文明岗。</p> ");


        arrayList.get(2).put("depId", "7753b67714463670cf0e84362b2fdeaa");
        arrayList.get(2).put("title", "/images/gmwwPage/new/name3.png");
        arrayList.get(2).put("name", "西柏坡纪念馆");
        arrayList.get(2).put("addr", "/images/gmwwPage/new/address3.png");
        arrayList.get(2).put("pano", "http://www.hb-museum.com/data/zip/44430aa2-acdb-4862-9d2f-911f3f7d6b0f/index.html");
        arrayList.get(2).put("bgImg", "/images/gmwwPage/new/pic3.png");
        arrayList.get(2).put("banner", "/images/gmwwPage/new/banner3.png");
        arrayList.get(2).put("intro", "位于河北省平山县，1955年设立，1978年5月26日对外开放。陈列展《新中国从这里走来》曾多次获奖。");
        arrayList.get(2).put("info", "<p>西柏坡位于河北省平山县境内，是解放战争时期中共中央和中国人民解放军总部的所在地。1955年，西柏坡纪念馆成立，1978年5月26日对外开放，是国家一级博物馆。</p>" +
                "<p>主题陈列《新中国从这里走来》曾于1993年、1996年、1998年、2003年、2009年进行了修改完善，被国家文物局评为“1998年度全国十大精品陈列”和“第六届（2003至2004年度）全国十大陈列展览特别奖。”</p>" +
                "<p>陈列展设立12个展室，每个展室均展出大量展品，系统反映了中共中央和领袖们在西柏坡期间的革命实践活动。展览内容以平山人民光辉的抗日斗争为铺垫，以解放战争后期中共中央在西柏坡时期的活动为主线，运用了大量的文物、照片和历史资料，辅之于绘画、雕塑、景观、幻影成像、半景画等高科技手段，重点介绍了中央工委、中共中央和毛泽东等老一辈革命家在西柏坡的伟大革命实践，同时将建国后社会各界参观瞻仰西柏坡和在这里举行的重大活动等情况进行展示。</p>");



        arrayList.get(0).put("depId", "5a6a467222854dcb9d76bef1a42fbd8c");
        arrayList.get(0).put("title", "/images/gmwwPage/new/name2.png");
        arrayList.get(0).put("name", "李大钊纪念馆");
        arrayList.get(0).put("addr", "/images/gmwwPage/new/address2.png");
        arrayList.get(0).put("pano", "http://www.hb-museum.com/data/zip/204776a8-78f5-4c1b-927c-f3f6a2b53e8e/index.html");
        arrayList.get(0).put("bgImg", "/images/gmwwPage/new/pic2.png");
        arrayList.get(0).put("banner", "/images/gmwwPage/new/banner2.png");
        arrayList.get(0).put("intro", "坐落于河北省乐亭县，于1996年8月18日奠基，1997年8月16日落成开馆, 2009年进行全面陈展改造。");
        arrayList.get(0).put("info", "<p>李大钊纪念馆坐落于河北省乐亭县新城区，于1996年8月18日奠基，1997年8月16日落成开馆,胡锦涛同志出席典礼仪式，2009年进行全面陈展改造。纪念馆占地130亩，建筑面积8656平方米。</p>" +
                "<p>纪念馆分为三个展厅，主要展示李大钊生平业绩。第一展厅为三个专题，分别是“国家危亡 历练成长”、“寻求救国救民真理”和“站在新文化运动最前列”，主要展示了李大钊出生时的社会背景、李大钊的求学之路以及在新文化运动时期的主要活动。</p>" +
                "<p>第二展厅有四个专题，分别是“中国最早的马克思主义传播者”、“中国共产党主要创始人之一”、“促成革命统一战线”和“领导北方革命运动”，主要展示了李大钊作为在中国传播马克思主义的先驱，通过参与创立中国共产党、促成革命统一战线和领导北方革命反帝反封建的事迹。</p>" +
                "<p>第三展厅有两个专题，分别是“献身人民解放事业”和“李大钊精神永存”，主要展示了李大钊在领导人民革命求解放的过程中从被捕到英勇就义以及后世对李大钊精神的传承。</p>");


        arrayList.get(4).put("depId", "409fa14eaa04996cd2e1fc0d85fda5fb");
        arrayList.get(4).put("title", "/images/gmwwPage/new/name12.png");
        arrayList.get(4).put("name", "冉庄地道战纪念馆");
        arrayList.get(4).put("addr", "/images/gmwwPage/new/address12.png");
        arrayList.get(4).put("pano", "http://www.hb-museum.com/data/zip/759370b8-c5e4-48f5-9eef-2b804324bc40/index.html");
        arrayList.get(4).put("bgImg", "/images/gmwwPage/new/pic12.png");
        arrayList.get(4).put("banner", "/images/gmwwPage/new/banner12.png");
        arrayList.get(4).put("intro", "位于河北省保定市清苑区冉庄村，于1959年设纪念馆，占地24亩，建筑面积近4000平方米。");
        arrayList.get(4).put("info", "<p>冉庄地道战纪念馆是革命历史纪念馆，位于河北省保定市清苑区冉庄村，1959年设纪念馆，占地24亩，建筑面积近4000平方米，展厅面积1800平方米。 </p>" +
                "<p>展厅利用丰富的陈展手法和幻影成像、三维图像、光电感应等高新技术向人们展示冀中地道战波澜壮阔的斗争史，颂扬了中国人民众志成城、不屈不挠的斗争精神。</p>" +
                "<p>馆内珍藏着革命文物361件，其中国家一级革命文物4件、二级革命文物17件，三级革命文物77件。重要藏品有：县武委会颁发的地道战首战告捷奖旗一面，当年发布动员及战斗号令的军号，还复制有地道战民兵战时装、子弹袋、土电话等文物，以及聂荣臻等老一辈革命家的题字、题词。</p>" +
                "<p>2017年12月，该馆入选教育部第一批全国中小学生研学实践教育基地、营地名单。现被列为全国爱国主义教育示范基地、河北省爱国主义教育基地。</p>");

        arrayList.get(9).put("depId", "0d9907820e574e46840f596fa797fc20");
        arrayList.get(9).put("title", "/images/gmwwPage/new/name6.png");
        arrayList.get(9).put("name", "华北革命战争纪念馆");
        arrayList.get(9).put("addr", "/images/gmwwPage/new/address6.png");
        arrayList.get(9).put("pano", "http://www.hbjqlsly.com/upload/zzjng/index.html");
        arrayList.get(9).put("bgImg", "/images/gmwwPage/new/pic6.png");
        arrayList.get(9).put("banner", "/images/gmwwPage/new/banner6.png");
        arrayList.get(9).put("intro", "位于河北省石家庄市华北烈士陵园内，始建于2011年3月，后在原晋察冀革命纪念馆基础上扩建。");
        arrayList.get(9).put("info", "<p>华北革命战争纪念馆是国家发改委确定的红色旅游改扩建项目，始建于2011年3月，2011年12月底主体建设完工，此馆是在原晋察冀革命纪念馆基础上进行扩建的，位于石家庄市华北烈士陵园的最北面。</p>" +
                "<p>纪念馆高20.5米，总面积5450平方米，展厅面积3000平方米。外墙上大型煅铜浮雕展示着1919年至1949年发生在华北大地上的标志性事件。</p>" +
                "<p>整个展览分为三个部分。第一部分“红旗插遍燕赵大地革命运动风起云涌”，集中展现了河北人民打倒列强、除军阀、工农运动以及第一次国共合作、建立革命统一战线等革命斗争史。</p>" +
                "<p>第二部分“抗日烽火燃烧燕赵万众一心抵御外辱”，河北人民踊跃参军支前，运用无穷的智慧，创造了地道战、地雷战、麻雀战、水上游击战等多种机动灵活的作战方式，配合八路军打击日伪军，成为抗战中一支重要力量。</p>" +
                "<p>第三部分“河北军民保卫解放区柏坡圣地孕育新中国”，突出展现了河北军民为全国解放战争的胜利所做的贡献。</p>" +
                "<p>展览共展出图片435幅，展品227件，雕塑创作10组，油画创作3幅，媒体播放系统10个，以历史图片、珍贵文物、雕塑、声光电等多种表现形式，让观众直观地了解历史。</p>");


        arrayList.get(10).put("depId", "b9f30984df2f2519bfa821cc7c4a18c5");
        arrayList.get(10).put("title", "/images/gmwwPage/new/name5.png");
        arrayList.get(10).put("name", "董存瑞纪念馆");
        arrayList.get(10).put("addr", "/images/gmwwPage/new/address5.png");
        arrayList.get(10).put("pano", "https://720yun.com/t/720judwuev6?scene_id=21976859");
        arrayList.get(10).put("bgImg", "/images/gmwwPage/new/pic5.png");
        arrayList.get(10).put("banner", "/images/gmwwPage/new/banner5.png");
        arrayList.get(10).put("intro", "位于河北省隆化县城西北苔山脚下，始建于1961年，1992年在革命烈士纪念馆旧址上重新修建。");
        arrayList.get(10).put("info", "<p>董存瑞纪念馆，位于河北省隆化县城西北的苔山脚下伊逊河东岸，始建于1961年，于1992年在革命烈士纪念馆旧址上重新修建。</p>" +
                "<p>现在的董存瑞纪念馆建于2008年，建筑面积为2300平方米，其建筑兼顾了董存瑞烈士陵园内其他不同时期、不同民族风格的建筑效果，重点体现汉唐时期的建筑特点。</p>" +
                "<p>馆内共有8个展厅，通过5大部分的内容和声、光、电、投影技术的配合，生动地讲述了董存瑞光辉的一生，新馆匾额采用原中共中央总书记、中央军委主席江泽民题写的馆名。</p>" +
                "<p>董存瑞纪念馆始终秉承“褒扬革命先烈、教育激励后人、传承存瑞精神、维护纪念设施”的宗旨，不断加强教育基地建设，挖掘红色文化内涵，拓展教育内容，丰富教育手段，使存瑞精神发扬光大。 </p>");


        arrayList.get(6).put("depId", "5413a1b6fbe0b6b01d50463d9225c030");
        arrayList.get(6).put("title", "/images/gmwwPage/new/name7.png");
        arrayList.get(6).put("name", "晋察冀边区革命纪念馆");
        arrayList.get(6).put("addr", "/images/gmwwPage/new/address7.png");
        arrayList.get(6).put("pano", "https://720.vrqjcs.com/t/d128165bdc273abf");
        arrayList.get(6).put("bgImg", "/images/gmwwPage/new/pic7.png");
        arrayList.get(6).put("banner", "/images/gmwwPage/new/banner7.png");
        arrayList.get(6).put("intro", "位于河北省阜平县城南庄村，始建于1972年，1974年正式对外开放，2005年改陈扩建。");
        arrayList.get(6).put("info", "<p>晋察冀边区革命纪念馆位于河北省阜平县城南20公里处的城南庄村，南临胭脂河，北依菩萨岭，群山环绕。</p>" +
                "<p>第一展馆建筑面积2200平方米，展线长260米，展出文物621件，照片222张，图表31块，紧紧围绕“模范抗日根据地--晋察冀边区”的主题进行布展，运用大量珍贵的照片、文物以及先进的声、光、电、幻影成像等技术，对展览内容进行详细生动的说明，集中反映了晋察冀根据地的光辉历史和晋察冀军民的丰功伟绩。</p>" +
                "<p>第二展馆建筑面积1550平方米，展线173米，展出图片188张，图表20个，文物115件，立体景观4个。紧紧围绕“全国解放战争的战略基地与指挥中心”这一主题，按照历史脉络，从抗战胜利后解放张家口到华北全境解放，共分为进驻张家口、保卫解放区、巩固壮大解放区、组建华北解放区、参加大决战、欢庆华北全境解放等六个章节，采取景观复原、触摸屏、雕塑等各种方式，充分展示了晋察冀解放区为解放战争胜利和新中国成立作出的卓越贡献。</p>" +
                "<p>晋察冀边区革命纪念馆现为全国爱国主义教育示范基地、国家AAAA级景区、全国重点文物保护单位、河北省国防教育示范基地、河北省廉政教育示范基地、全国中小学研学实践基地。</p>");


        arrayList.get(3).put("depId", "c1dee452b575ba85caf129be6d960303");
        arrayList.get(3).put("title", "/images/gmwwPage/new/name9.png");
        arrayList.get(3).put("name", "留法勤工俭学运动纪念馆");
        arrayList.get(3).put("addr", "/images/gmwwPage/new/address9.png");
        arrayList.get(3).put("pano", "http://www.expoon.com/6704/panorama");
        arrayList.get(3).put("bgImg", "/images/gmwwPage/new/pic9.png");
        arrayList.get(3).put("banner", "/images/gmwwPage/new/banner9.png");
        arrayList.get(3).put("intro", "位于保定市育德中学旧址，是全国唯一宣传、展示、收藏、研究留法勤工俭学运动史的纪念展馆。");
        arrayList.get(3).put("info", "<p>留法勤工俭学运动纪念馆，位于河北省保定市金台驿街86号育德中学旧址，是全国唯一宣传、展示、收藏、研究留法勤工俭学运动史的纪念展馆。1983年2月，经中共中央书记处批准建馆。1992年6月，时任中共中央总书记江泽民同志题写馆名。</p>" +
                "<p>馆内设有两项展览，分别为基本陈列——留法勤工俭学运动史料陈列展和专题陈列——育德中学校史展。留法勤工俭学运动史料陈列是全国唯一一部系统、全面反映留法勤工俭学运动始末的展览，赞颂了以蔡和森、赵世炎、周恩来、陈毅、聂荣臻以及我国改革开放的总设计师邓小平同志为代表的老一辈共产党人的革命精神和光辉业绩；育德中学校史展在文物本体——“幼云堂”内布展，介绍近代华北名校育德中学的百年校史。</p>" +
                "<p>留法勤工俭学运动纪念馆现为“全国重点文物保护单位”、“全国红色旅游经典景区”、“国家三级博物馆”、“中国侨联爱国主义教育基地”、“河北省爱国主义教育基地”、“河北省中共党史教育基地”，在革命传统教育、爱国主义教育和青少年思想道德教育等方面发挥着越来越重要的作用。</p>");



        arrayList.get(5).put("depId", "f843fb5797de447f4abdc91e19e1bbae");
        arrayList.get(5).put("title", "/images/gmwwPage/new/name13.png");
        arrayList.get(5).put("name", "中国人民抗日军政大学陈列馆");
        arrayList.get(5).put("addr", "/images/gmwwPage/new/address13.png");
        arrayList.get(5).put("pano", "http://www.hb-museum.com/data/zip/76b7a091-318f-4b58-85a7-c08c0e9d4e24/index.html");
        arrayList.get(5).put("bgImg", "/images/gmwwPage/new/pic13.png");
        arrayList.get(5).put("banner", "/images/gmwwPage/new/banner13.png");
        arrayList.get(5).put("intro", "位于河北省邢台县前南峪村，是全国建馆最早、规模最大、全面反映抗大发展史的专题性陈列馆。");
        arrayList.get(5).put("info", "中国人民抗日军政大学陈列馆简称“抗大陈列馆”，为国家二级博物馆，位于河北省邢台县浆水镇前南峪村，距邢台市区60公里，展陈面积2888平方米，是全国建馆最早、规模最大、全面反映抗大发展史的专题性陈列馆。</p>" +
                "<p>陈列馆共设三个展厅，分别为序厅、主题厅和西展厅。展出图片、实物、文物1290件，包括战士生活用具、武器、教学笔记、相关书刊等。陈列馆拥有科目最全、数量最多的珍贵文献——抗大教材。</p>" +
                "<p>陈列馆运用半场景、场景复原等艺术手法和声、光、电等现代陈列手段再现了抗大可歌可泣的战斗历程和发展成就。</p>" +
                "<p>现为“全国爱国主义教育示范基地”、“国家级国防教育基地”、“国防大学现地教学基地”、首批“全国百个红色旅游景区”、“国家国防教育示范基地”、“全国中小学生研学实践教育基地”、“第三批省级文物保护单位”。</p>");


        arrayList.get(8).put("depId", "5004af304afee4c10a48002c2f9e572c");
        arrayList.get(8).put("title", "/images/gmwwPage/new/name8.png");
        arrayList.get(8).put("name", "晋冀鲁豫陈列馆");
        arrayList.get(8).put("addr", "/images/gmwwPage/new/address8.png");
        arrayList.get(8).put("pano", "https://720yun.com/t/gygmaploljh5yazws9?pano_id=TOtWNiNcmTaKx7Yo");
        arrayList.get(8).put("bgImg", "/images/gmwwPage/new/pic8.png");
        arrayList.get(8).put("banner", "/images/gmwwPage/new/banner8.png");
        arrayList.get(8).put("intro", "位于晋冀鲁豫烈士陵园内，于1946年在原侵华日军东亚神社遗址上修建而成。");
        arrayList.get(8).put("info", "<p>晋冀鲁豫陈列馆位于晋冀鲁豫烈士陵园内，陈列馆造型秀美、幽雅，是一座富有民族传统风格的古典庭院建筑，1946年在原侵华日军东亚神社遗址上修建而成。馆名为刘伯承所题。馆内是《晋冀鲁豫革命史迹陈列》。</p>" +
                "<p>晋冀鲁豫烈士陵园，是按照党的七大精神，为纪念牺牲在晋冀鲁豫边区的八路军总部前方司令部、政治部、晋冀鲁豫军区及129师的革命烈士，由晋冀鲁豫边区参议会决议修建的，是抗日战争胜利后中国共产党首座宏大革命纪念建筑；是新中国建设较早、规模较大、建筑艺术较高、环境较美的全国著名革命烈士纪念地；是第一批“国家级烈士纪念设施保护单位”、“全国百家红色旅游经典景区”、“全国爱国主义教育示范基地”、“国家国防教育示范基地”、4A国家级旅游景区和“国家级抗战纪念设施”。</p>");

        arrayList.get(7).put("depId", "f153d393b7a1eab8c829b50a0338208c");
        arrayList.get(7).put("title", "/images/gmwwPage/new/name4.png");
        arrayList.get(7).put("name", "白求恩柯棣华纪念馆");
        arrayList.get(7).put("addr", "/images/gmwwPage/new/address4.png");
        arrayList.get(7).put("pano", "https://pano.weapp.360vrsh.com/Home/Pano/outUrl/id/31108.html");
        arrayList.get(7).put("bgImg", "/images/gmwwPage/new/pic4.png");
        arrayList.get(7).put("intro", "坐落于河北省唐县城北，始建于1971年，1985年移址扩建，1986年11月新馆建成，占地面积45950平方米。");
        arrayList.get(7).put("banner", "/images/gmwwPage/new/banner4.png");
        arrayList.get(7).put("info", "<p>白求恩柯棣华纪念馆坐落在唐县城北2公里钟鸣山下。纪念馆始建于1971年，1985年移址扩建，1986年11月新馆建成，同年与白求恩纪念馆结为姊妹馆向社会开放。</p>" +
                "<p>纪念馆占地面积45950平方米，主体建筑1818平方米，整个建筑群采用中国传统的民族形式。原中央总书记胡耀邦题写的“唐县白求恩柯棣华纪念馆”馆名镶嵌在门额正中。主建筑分为“两馆一堂”，北侧中央是纪念堂；西侧是白求恩纪念馆；东侧为柯棣华纪念馆。</p>" +
                "<p>馆内藏品丰富，展出图片300余幅，实物近百件。其中有白求恩当年用过的手术器械、消毒锅、煤油灯；有柯棣华当年使用过的医药箱、医疗用品等。这些文物和展品，完整地记录了这两位国际主义战士光辉的生命轨迹，陶冶着人们的情操，激励着人们的斗志。</p>" +
                "<p>1995年，纪念馆被确定为“河北省爱国主义教育基地”，1997年6月，被中宣部命名为全国“百个爱国主义教育示范基地”之一。2017年1月，白求恩柯棣华纪念馆入选“全国红色旅游经典景区名录。”</p>");


        arrayList.get(11).put("depId", "ee585e64ddcb204cefe0c18d99b9d9d9");
        arrayList.get(11).put("title", "/images/gmwwPage/new/name11.png");
        arrayList.get(11).put("name", "潘家峪惨案纪念馆");
        arrayList.get(11).put("addr", "/images/gmwwPage/new/address11.png");
        arrayList.get(11).put("pano", "http://www.hb-museum.com/data/zip/c77d4584-94c7-4ac5-ad8c-3aac617aa6cc/index.html");
        arrayList.get(11).put("bgImg", "/images/gmwwPage/new/pic11.png");
        arrayList.get(11).put("banner", "/images/gmwwPage/new/banner11.png");
        arrayList.get(11).put("intro", "位于河北省唐山市丰润区火石营镇潘家峪村，为纪念“潘家峪惨案”中的死难同胞，于1999年建成。");
        arrayList.get(11).put("info", "<p>潘家峪惨案纪念馆位于河北省唐山市丰润区火石营镇潘家峪村中部，建筑面积1246平方米。1941年1月25日，灭绝人性的侵华日军包围了潘家峪，对手无寸铁的村民进行了惨绝人寰的大屠杀，1230名同胞遇难，制造了震惊中外的“潘家峪惨案”。为纪念惨案中死难同胞，于1999年建成潘家峪惨案纪念馆。</p>" +
                "<p>《潘家峪惨难史基本陈列》一层展厅展示了1941年1月25日潘家峪惨案的过程。展厅顶部均涂为灰色，展厅内全部采用人工照明，通过声、光、电等展示手段，营造沉重氛围。二层展厅表现惨案后潘家峪人民及复仇团不屈不挠的反抗斗争，直至取得最后的胜利的内容。尾厅展示了今日潘家峪人民幸福美好的新生活。展厅顶部涂为红色，形成与一层主展厅空间强烈的明暗对比，喻示由黑暗走向光明。在二层平台，观众可凭栏远眺惨案发生地“潘家大院”的全貌，凭吊死难的同胞。</p>" +
                "<p>潘家峪惨案纪念馆，2001年6月被中宣部命名为全国爱国主义教育示范基地，2005年被国家13部委确定为全国首批百处红色旅游经典景区之一；2014年6月，被中共河北省委命名为“中共河北省委党史教育基地”。");


        arrayList.get(12).put("depId", "c97e85f493336cc0a3feb701972663c9");
        arrayList.get(12).put("title", "/images/gmwwPage/new/name10.png");
        arrayList.get(12).put("name", "梅花惨案纪念馆");
        arrayList.get(12).put("addr", "/images/gmwwPage/new/address10.png");
        arrayList.get(12).put("pano", "http://www.redian720.com/pano/demo/mhca/?from=singlemessage&isappinstalled=0");
        arrayList.get(12).put("bgImg", "/images/gmwwPage/new/pic10.png");
        arrayList.get(12).put("banner", "/images/gmwwPage/new/banner10.png");
        arrayList.get(12).put("intro", "位于河北省石家庄市藁城区梅花镇梅花村，该馆以“梅花惨案”为历史背景并在其遗址基础上修建。");
        arrayList.get(12).put("info", "<p>梅花惨案纪念馆，位于石家庄市藁城区梅花镇梅花村村东，该馆始建于1958年，占地面积27.9亩，总建筑面积736平方米。</p>" +
                "<p>“梅花惨案”遗址是日本侵略者屠杀中国人民的历史见证之一，纪念馆以梅花惨案为历史背景并在“梅花惨案遗址”的基础上修建。1982年河北省人民政府将其公布为河北省重点文物保护单位，1994年又被河北省政府批准为省级爱国主义教育基地。2015年列入第二批国家级抗战纪念设施、遗址名录。</p>" +
                "<p>展厅分为四部分，第一部分“沃野明珠”，以沙盘再现惨案前梅花古镇的雄姿和繁荣；第二部分“铁狮怒吼”，以翔实的历史资料，再现梅花惨案的历史背景及吕正操率领部队，阻止日军南侵，毙伤敌军800余名，打破日军不可战胜的神话；第三部分“九九血证”，揭露日寇制造“九九”(农历九月初九)梅花惨案的滔天罪行；第四部分“历史昭示”，广大人民群众在党的领导下，打败日本侵略者，建立人民政权，过上幸福美满的生活，应牢记“落后就要挨打”，以史为鉴，不忘国耻，奋发有为。</p>" +
                "</p>");

        return arrayList;
    }


}
