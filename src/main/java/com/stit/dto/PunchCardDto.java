package com.stit.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class PunchCardDto {
	private String empId ;
	private LocalDateTime punchTime ;
	private BigDecimal lat ;
	private BigDecimal lng;
	private String kind;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public LocalDateTime getPunchTime() {
		return punchTime;
	}

	public void setPunchTime(LocalDateTime punchTime) {
		this.punchTime = punchTime;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}


	@Override
	public int hashCode() {
		int hash = 7;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PunchCardDto other = (PunchCardDto) obj;
		if (!Objects.equals(this.empId, other.empId)) {
			return false;
		}
		return Objects.equals(this.punchTime, other.punchTime);
	}

	@Override
	public String toString() {
		return "PunchCardDto{" + "empId=" + empId + ", punchTime=" + punchTime + ", lat=" + lat + ", lng=" + lng + ", kind=" + kind + '}';
	}
	
}
