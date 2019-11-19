package priv.xly.rentsys.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import priv.xly.rentsys.model.HouseOwner;

@Repository
public interface HouseOwnerDao {
	//String passwd, String name ,String picUrl,String address,String phoneNum
	@Insert("insert into house_owner(id,passwd,pic_url,name,address,phone_num) "
			+ "values(null,#{passwd},#{picUrl},#{name},#{address},#{phoneNum})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void insert(HouseOwner h);

	@Update("update house_owner set name=#{name},address=#{address},phone_num=#{phoneNum}"
			+ ",pic_url=#{picUrl} where id=#{id}")
	public void update(HouseOwner owner);

	@Select("select * from house_owner where id=#{id}")
	public HouseOwner get(@Param("id") int id);

}
