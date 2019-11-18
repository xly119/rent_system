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
import priv.xly.rentsys.model.house.HouseLeaseRecord;
import priv.xly.rentsys.model.house.HouseVisitRecord;

@Repository
public interface HouseDao {

	
	@Insert("insert into house(id,owner_id,max_tenant_num,rent,type,state,pic_url) "
			+ "values(null,#{ownerId},#{maxTenantNum},#{phoneNum},#{type},#{state},,#{picUrl})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void insertHouse(House house);
	
	@Insert("insert into visits(id,house_id,owner_id,vister_id,visit_time,state) "
			+ "values(null,#{houseId},#{ownerId},#{visterId},#{visitTime},#{state})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void insertVisit(HouseVisitRecord visit);
	
	@Insert("insert into lease(id,house_id,tenant_id,start_date,end_date) "
			+ "values(null,#{houseId},#{tenantId},#{startDate},#{endDate})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void insertLease(HouseLeaseRecord visit);
	

	@Update("update house set max_tenant_num=#{maxTenantNum},"
			+ "rent=#{rent},pic_url=#{picUrl} where id=#{id}")
	public void updateHouseInfo(House house);
	
	@Update("update house set state=#{state} where id=#{id}")
	public void updateHouseState(@Param("state") int state,@Param("id") int id);


	@Update("update visits set state=#{state} where id=#{id}")
	public void updateVisitState(@Param("state") int state,@Param("id") int id);
	
	@Select("select * from house where owner_id=#{id}")
	public List<House> getHouses(@Param("id") int id);
	
	@Select("select * from house where id=#{id}")
	public House getHouseById(@Param("id") int id);
	
	@Select("select * from lease where house_id=#{id}")
	public List<HouseLeaseRecord> getLeases(@Param("id") int id);
	
	@Select("select * from visits where house_id=#{id}")
	public List<HouseVisitRecord> getVisits(@Param("id") int id);
	
	@Select("select * from visits where owner_id=#{id}")
	public List<HouseVisitRecord> getVisitsByOwner(@Param("id") int id);
	
	@Delete("delete from  house where id=#{id}")
    public void deleteHouse(@Param("id") int id);
	
}
