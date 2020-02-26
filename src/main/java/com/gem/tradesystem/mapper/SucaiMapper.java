package com.gem.tradesystem.mapper;

import com.gem.tradesystem.entity.Sucai;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface SucaiMapper {
    @Update("update sucai set favnum=favnum+1 WHERE id=#{id}")//根据sucai的id更新sucai表中的点赞数
    int updateFav(Integer id);

    @Insert("INSERT into fav(sucaiid,userid) values(#{sucaiid},#{userid})")//在fav表中插入一条点赞记录
    int insertOneFav(@Param("sucaiid") Integer sucaiid, @Param("userid")Integer userid);

    @Select("select * from sucai where id=#{id}")//根据id获取素材
    Sucai getOneById(Integer id);

    @Select("select * from sucai where name like CONCAT('%',#{search},'%')")//根据输入的素材名称模糊查询
    List<Sucai> getSearchList(String search);

    @Select("select count(*) from sucai where name like CONCAT('%',#{search},'%')")//获取输入的素材名称模糊查询的素材数目
    Integer getSearchCount(String search);

    @Select("select * from sucai where name like CONCAT('%',#{search},'%') limit #{page},#{size}")//根据输入的素材名称模糊分页查询
    List<Sucai> getSearchPageList(String search,Integer page,Integer size);

    @Select("select distinct typename from tag where pid='0'")//获取一级菜单
    List<String> getMenu();

    @Select("select typename from tag b where b.pid=(SELECT id from tag a where typename=#{typename})")//获取一级菜单对应下的二级菜单
    List<String> getSubMenu(String typename);

    @Select("select typename from tag b where b.pid!='0'")//获取所有的二级菜单，显示在全部下
    List<String> getAllSubMenu();

    @Select("select * from sucai where id in(select sucaiid from tagjb where tagid in(select id from tag where typename=#{typename}))")//获取二级菜单下所有的素材
    List<Sucai> getSubMenuList(String typename);

    @Select("SELECT * from sucai where id in(" +
            "SELECT sucaiid from tagjb where tagid in " +
            "(select id from tag where pid=(SELECT id from tag where typename=#{typename})))")//获取一级菜单下所有的素材
    List<Sucai> getMenuList(String typename);

    @Select("select * from sucai")
    List<Sucai> getList();//获取全部素材

    @Select("select * from sucai limit #{page},#{size}")
    List<Sucai> getPageList(Integer page,Integer size);//分页获取全部素材

    @Select("select * from sucai where id in(select sucaiid from tagjb where tagid in(select id from tag where typename=#{typename})) limit #{page},#{size}")//分页获取二级菜单下所有的素材
    List<Sucai> getSubMenuPageList(String typename,Integer page,Integer size);

    @Select("SELECT * from sucai where id in(" +
            "SELECT sucaiid from tagjb where tagid in " +
            "(select id from tag where pid=(SELECT id from tag where typename=#{typename}))) limit #{page},#{size}")//分页获取一级菜单下所有的素材
    List<Sucai> getMenuPageList(String typename,Integer page,Integer size);

    @Select("select count(*) from sucai")
    Integer getCount();//所有素材总数

    @Select("select count(*) from sucai where id in(select sucaiid from tagjb where tagid in(select id from tag where typename=#{typename}))")//获取二级菜单下所有的素材数目
    Integer getSubMenuCount(String typename);

    @Select("SELECT count(*) from sucai where id in(" +
            "SELECT sucaiid from tagjb where tagid in " +
            "(select id from tag where pid=(SELECT id from tag where typename=#{typename})))")//获取一级菜单下所有的素材数目
    Integer getMenuCount(String typename);

    @Select("SELECT typename from tag where id in(SELECT tagid from tagjb where sucaiid=#{sucaiid})")//获取素材的标签列表
    List<String> getTags(Integer sucaiid);

    @Select("SELECT typename from tag where id in (SELECT pid from tag where id in(SELECT id from tag where typename=#{typename}))")//获取二级菜单的父菜单
    String getPMenu(String typename);


    Sucai getOneSu(Integer id);

}
