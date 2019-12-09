package priv.xly.rentsys.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import priv.xly.rentsys.model.Tenant;

@Repository
public interface TenantDao {
	

	@Insert("insert into tenant(id,passwd,pic_url,name,address,phone_num,birthday,sex)"
			+ " values(null,#{passwd},#{picUrl},#{name},#{address},#{phoneNum},#{birthday},#{sex})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void insert(Tenant t);

	@Update("update tenant set name=#{name},address=#{address},phone_num=#{phoneNum},birthday=#{birthday},passwd=#{passwd},"
			+ "sex=#{sex},pic_url=#{picUrl} where id=#{id}")
	public void update(Tenant t);

	@Select("select * from tenant where id=#{id}")
	public Tenant get(@Param("id") int id);
	
	@Select("select * from tenant where phone_num=#{phoneNum}")
	public Tenant getByPhoneNum(@Param("phoneNum") String phoneNum);

}
