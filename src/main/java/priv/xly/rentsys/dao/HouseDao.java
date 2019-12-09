package priv.xly.rentsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import priv.xly.rentsys.model.house.House;
import priv.xly.rentsys.model.house.HouseVisitRecord;

@Repository
public interface HouseDao {

	
	@Insert("insert into house(id,owner_id,max_tenant_num,rent,type,state,pic_url,address) "
			+ "values(null,#{ownerId},#{maxTenantNum},#{rent},#{type},#{state},#{picUrl},#{address})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void insertHouse(House house);
	
	@Insert("insert into visits(id,house_id,owner_id,visiter_id,visit_time,state,mark,address) "
			+ "values(null,#{houseId},#{ownerId},#{visiterId},#{visitTime},#{state},#{mark},#{address})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void insertVisit(HouseVisitRecord visit);
	
	@Insert("insert into subscribe(id,house_id,user_id) values(null,#{houseId},#{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void insertSubs(@Param("userId") int userId,@Param("houseId") int houseId);

	@Update("update house set max_tenant_num=#{maxTenantNum},type=#{type},"
			+ "rent=#{rent},pic_url=#{picUrl} where id=#{id}")
	public void updateHouseInfo(House house);
	
	@Update("update house set state=#{state} where id=#{id}")
	public void updateHouseState(@Param("state") int state,@Param("id") int id);
	
	@Update("update house set subs=subs+#{move} where id=#{id}")
	public void updateHouseSubs(@Param("move")int move,@Param("id") int id);

	@Update("update visits set state=#{state} where id=#{id}")
	public void updateVisitState(@Param("state") int state,@Param("id") int id);
	
	@Select("select * from house where owner_id=#{id}")
	public List<House> getHouses(@Param("id") int id);
	
	@Select("select * from house where id=#{id}")
	public House getHouseById(@Param("id") int id);
	
	@Select("select * from house where state=1")
	public List<House> getHouseAvailable();
	
	@Select("select * from house h inner join subscribe s on h.id=s.house_id "
			+ "and s.user_id=#{userId}")
	public List<House> getHouseSubscribed(@Param("userId") int userId);
	
	@Select("select * from visits where house_id=#{id}")
	public List<HouseVisitRecord> getVisits(@Param("id") int id);
	
	@Select("select * from visits where owner_id=#{id} and state !=2")
	public List<HouseVisitRecord> getVisitsByOwner(@Param("id") int id);
	
	@Select("select * from visits where visiter_id=#{id}")
	public List<HouseVisitRecord> getVisitsByVisiter(@Param("id") int id);
	
	@Select("select id from subscribe where user_id=#{userId} and house_id=#{houseId}")
	public Object getSubsById(@Param("userId") int userId,@Param("houseId") int houseId);
	
	@Delete("delete from  house where id=#{id}")
    public void deleteHouse(@Param("id") int id);
	
	@Delete("delete from  subscribe where user_id=#{userId} and house_id=#{houseId}")
    public void deleteSubs(@Param("userId") int userId,@Param("houseId") int houseId);
}
