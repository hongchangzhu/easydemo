package com.analysis.service;

import java.util.List;

import com.analysis.dao.TermResourceDaoImpl;
import com.analysis.po.QueryCondition;
import com.analysis.po.StatResult;
import com.analysis.utils.Constant;
import com.analysis.utils.Query;

public class FusionChartsService{
	public String getChartsXML(QueryCondition cond){
		TermResourceDaoImpl daoImpl = new TermResourceDaoImpl();
		Query query = new Query();
		String sql = query.getSql(cond);
		List condParams = query.getCondParams();

		List<StatResult> list = daoImpl.stat(sql, condParams);
		String axixName = this.getXAxisName(cond);
		StringBuffer sb = new StringBuffer();
		sb.append("<chart caption=\"终端站点应用统计\" xAxisName=\"");
		sb.append(axixName);
		sb
				.append("\" yAxisName=\"数量\" showValues=\"1\" showBorder=\"1\" decimals=\"0\" formatNumberScale=\"0\" baseFontSize=\"12\" baseFont=\"宋体\">");

		for(StatResult res : list){
			sb.append("<set label=\"").append(res.getName()).append(
					"\" value=\"").append(res.getTotalNum()).append("\" />");
		}
		sb.append("</chart>");
		return sb.toString();
	}

	private String getXAxisName(QueryCondition cond){
		String name = "";
		String opttype = cond.getOpttypeid();
		if(Constant.TAKE_OVER_TYPE.equals(opttype)){
			name = "已接收资源";
		}else if(Constant.UN_BOOT_STRAP_TYPE.equals(opttype)){
			name = "未开机终端";
		}else if(Constant.CLICKED_COUNT_TYPE.equals(opttype)){
			name = "资源点击量";
		}else if(Constant.DOWNLOAD_COUNT_TYPE.equals(opttype)){
			name = "资源下载量";
		}

		return name;
	}
}
