package com.jaycon.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;


@Entity
@Table
public class product_parameters implements Serializable{
	@Id
	@GenericGenerator(name="customUUID", strategy="uuid2")
	@GeneratedValue(generator="customUUID")
	private String Product_Id;
	private String quoteId;
	private String x;
	private String y;
	private String z;
	private String molding_tooling;
	private String preproduction;
	private String startdate;
	private String certificates;
	private String material;
	private String color;
	private String part_name;
	private String total_units;
	private String x_mold_core;
	private String y_mold_core;
	private String z_mold_core;
	private String volume_cubic_mm;
	private String cubic_cm;
	private String cubic_m;
	private String cost_undercut;
	private String undercuts;
	private String density_of_mold_core;
	private String cost_per_kg_of_core;
	private String sprue_volume;
	private String runner_volume;
	private String part_cavity_volume;
	private String number_of_cavities;
	private String density_of_material;
	private String cost_of_material_per_kg;
	private String number_of_parts;
	private String number_of_shots;
	private String material_cost_per_shot;
	private String material_cost_total;
	private String volume;
	private String auction_permit;
	private String email;
	private String Name;
	private String phoneNo;
	private String partRevision;
	
	
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
	@com.fasterxml.jackson.annotation.JsonFormat(with = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private QuoteCosts quoteCosts;
	
	public product_parameters(){}
	 
	
	public String getPartRevision() {
		return partRevision;
	}


	public void setPartRevision(String partRevision) {
		this.partRevision = partRevision;
	}


	public QuoteCosts getQuoteCosts() {
		return quoteCosts;
	}


	public void setQuoteCosts(QuoteCosts quoteCosts) {
		this.quoteCosts = quoteCosts;
	}


	public String getProduct_Id() {
		return Product_Id;
	}
	public void setProduct_Id(String product_Id) {
		Product_Id = product_Id;
	}
	
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getAuction_permit() {
		return auction_permit;
	}

	public void setAuction_permit(String auction_permit) {
		this.auction_permit = auction_permit;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
	}

	public String getUndercuts() {
		return undercuts;
	}

	public void setUndercuts(String undercuts) {
		this.undercuts = undercuts;
	}

	public String getMolding_tooling() {
		return molding_tooling;
	}
	public void setMolding_tooling(String molding_tooling) {
		this.molding_tooling = molding_tooling;
	}
	public String getPreproduction() {
		return preproduction;
	}
	public void setPreproduction(String preproduction) {
		this.preproduction = preproduction;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	
	public String getCertificates() {
		return certificates;
	}
	public void setCertificates(String certificates) {
		this.certificates = certificates;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPart_name() {
		return part_name;
	}
	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
	public String getTotal_units() {
		return total_units;
	}
	public void setTotal_units(String total_units) {
		this.total_units = total_units;
	}
	public String getX_mold_core() {
		return x_mold_core;
	}
	public void setX_mold_core(String x_mold_core) {
		this.x_mold_core = x_mold_core;
	}
	public String getY_mold_core() {
		return y_mold_core;
	}
	public void setY_mold_core(String y_mold_core) {
		this.y_mold_core = y_mold_core;
	}
	public String getZ_mold_core() {
		return z_mold_core;
	}
	public void setZ_mold_core(String z_mold_core) {
		this.z_mold_core = z_mold_core;
	}
	public String getVolume_cubic_mm() {
		return volume_cubic_mm;
	}
	public void setVolume_cubic_mm(String volume_cubic_mm) {
		this.volume_cubic_mm = volume_cubic_mm;
	}
	public String getCubic_cm() {
		return cubic_cm;
	}
	public void setCubic_cm(String cubic_cm) {
		this.cubic_cm = cubic_cm;
	}
	public String getCubic_m() {
		return cubic_m;
	}
	public void setCubic_m(String cubic_m) {
		this.cubic_m = cubic_m;
	}
	public String getCost_undercut() {
		return cost_undercut;
	}
	public void setCost_undercut(String cost_undercut) {
		this.cost_undercut = cost_undercut;
	}
	public String getDensity_of_mold_core() {
		return density_of_mold_core;
	}
	public void setDensity_of_mold_core(String density_of_mold_core) {
		this.density_of_mold_core = density_of_mold_core;
	}
	public String getCost_per_kg_of_core() {
		return cost_per_kg_of_core;
	}
	public void setCost_per_kg_of_core(String cost_per_kg_of_core) {
		this.cost_per_kg_of_core = cost_per_kg_of_core;
	}
	
	
	
	public String getSprue_volume() {
		return sprue_volume;
	}
	
	public void setSprue_volume(String sprue_volume) {
		this.sprue_volume = sprue_volume;
	}
	
	public String getRunner_volume() {
		return runner_volume;
	}
	
	public void setRunner_volume(String runner_volume) {
		this.runner_volume = runner_volume;
	}
	
	public String getPart_cavity_volume() {
		return part_cavity_volume;
	}
	
	public void setPart_cavity_volume(String part_cavity_volume) {
		this.part_cavity_volume = part_cavity_volume;
	}
	
	public String getNumber_of_cavities() {
		return number_of_cavities;
	}
	
	public void setNumber_of_cavities(String number_of_cavities) {
		this.number_of_cavities = number_of_cavities;
	}
	
	public String getDensity_of_material() {
		return density_of_material;
	}
	
	public void setDensity_of_material(String density_of_material) {
		this.density_of_material = density_of_material;
	}
	
	public String getCost_of_material_per_kg() {
		return cost_of_material_per_kg;
	}
	
	public void setCost_of_material_per_kg(String cost_of_material_per_kg) {
		this.cost_of_material_per_kg = cost_of_material_per_kg;
	}
	
	public String getNumber_of_parts() {
		return number_of_parts;
	}
	
	public void setNumber_of_parts(String number_of_parts) {
		this.number_of_parts = number_of_parts;
	}
	
	public String getNumber_of_shots() {
		return number_of_shots;
	}
	
	public void setNumber_of_shots(String number_of_shots) {
		this.number_of_shots = number_of_shots;
	}
	
	public String getMaterial_cost_per_shot() {
		return material_cost_per_shot;
	}
	
	public void setMaterial_cost_per_shot(String material_cost_per_shot) {
		this.material_cost_per_shot = material_cost_per_shot;
	}
	
	public String getMaterial_cost_total() {
		return material_cost_total;
	}
	
	public void setMaterial_cost_total(String material_cost_total) {
		this.material_cost_total = material_cost_total;
	}
	
	
}
