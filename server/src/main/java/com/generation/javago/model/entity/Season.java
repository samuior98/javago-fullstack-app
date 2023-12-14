package com.generation.javago.model.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.generation.javago.library.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe/entità Season, definisce un periodo dell'anno durante il quale <br>
 * è applicato un sovraprezzo per le prenotazioni.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Season extends BaseEntity
{
	private static final int MAXDURATION = 21;

	private char tag;
	private String name;
	private LocalDate beginDate, endDate;
	private Double factor;

	@Override
	public List<String> getErrors()
	{
		List<String> errors= new ArrayList<>();
		if(tag==0)
			errors.add("-Missing or invalid tag for field 'tag'");
		if(!hasValue(name))
			errors.add("-Missing or invalid value for field 'name'");
		if(beginDate == null)
			errors.add("-Missing or invalid value for field 'beginDate'");
		if(endDate == null)
			errors.add("-Missing or invalid value for field 'endDate'");
		if(beginDate.isAfter(endDate) || beginDate.isEqual(endDate))
			errors.add("-Invalid values for fields 'endDate','beginDate'");
		if(beginDate.until(endDate, ChronoUnit.DAYS)>MAXDURATION)
			errors.add("-Season duration must not exceed 21 days");
		if(factor <= 0)
			errors.add("-Missing or invalid value for field 'factor'");

		return errors;
	}

	// 10/09/2023   ->   gg/mm/aaaa
	public void setBeginDate(String date)
	{
		try
		{
			String[] d= date.split("/");
			LocalDate ld= LocalDate.parse(d[2] + "-" + d[1] + "-" + d[0]);
			this.beginDate= ld;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Data format not valid. Correct format dd/mm/yyyy");
		}
	}


	public void setEndDate(String date)
	{
		try
		{
			String[] d= date.split("/");
			LocalDate ld= LocalDate.parse(d[2] + "-" + d[1] + "-" + d[0]);
			this.endDate= ld;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Data format not valid. Correct format dd/mm/yyyy");
		}
	}

}