package com.syhc.common.tools;

import java.io.IOException;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 自定义分页标签工具类
 * 
 * @Copyright MacChen
 * 
 * @Project CodeGenerationTool
 * 
 * @Author MacChen
 * 
 * @timer 2017-12-01
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 8.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class PageLimitTag extends TagSupport {

	private static final long serialVersionUID = 5405040340427645789L;

	public static final Integer DEFAULT_DISPLAY_ROW = 15;

	public PageLimitTag() {
		super();
	}

	public PageLimitTag(String actionPath, int totalRows, int pageSize, int pageIndex) {
		super();
		this.actionPath = actionPath;
		this.totalRows = totalRows;
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
		this.reset();
	}

	/** 页面表单标识 */
	private String formId;

	/** 业务连接地址 */
	private String actionPath;

	/** 数据总记录数 */
	private int totalRows;

	/** 页面记录数 */
	private int pageSize = DEFAULT_DISPLAY_ROW;

	/** 数据总分页数 */
	private int totalPage;

	/** 当前页页号 */
	private int pageIndex;

	/** 当前页起始行号 */
	private int startRow;

	/** 当前页结束行号 */
	private int endRow;

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public void reset() {
		if (this.totalRows != 0 && this.pageSize != 0) {
			this.totalPage = this.totalRows % this.pageSize == 0 ? this.totalRows / this.pageSize : this.totalRows / this.pageSize + 1;
			if (this.totalRows <= this.pageSize) {
				this.startRow = 0;
				this.endRow = this.totalRows;
			} else if (this.pageIndex == 1) {
				this.startRow = 0;
				this.endRow = this.pageSize;
			} else {
				this.startRow = (this.pageIndex - 1) * this.pageSize;
				this.endRow = this.pageIndex * this.pageSize > this.totalRows ? this.totalRows : this.pageIndex * this.pageSize;
			}
		}
	}

	public int doEndTag() {
		this.reset();
		StringBuilder outcontent = new StringBuilder();
		outcontent.append("<table border=\"0\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"vertical-align:middle;width:100%;height:30px;border:1px solid #718da6;background-color: #E9F5D1;\">");
		outcontent.append("<tr>");
		outcontent.append("<td width=\"20px\">&nbsp;</td>");
		outcontent.append("<td width=\"250px\" align=\"center\">");
		if (this.pageIndex != 1 && this.totalPage > 1) {
			outcontent.append("<a href =\"javascript:void(0);\"><img src=\"" + this.pageContext.getServletContext().getContextPath() + "/images/manager/limit/first.gif\" onclick=\"gotoFirstPage()\"/></a>");
		} else {
			outcontent.append("<a href =\"javascript:void(0);\"><img src=\"" + this.pageContext.getServletContext().getContextPath() + "/images/manager/limit/first_disable.gif\"\"/></a>");
		}
		outcontent.append("&nbsp;&nbsp;&nbsp;");
		if (this.pageIndex != 1 && this.totalPage > 1 && this.totalRows > this.pageSize) {
			outcontent.append("<a href =\"javascript:void(0);\"><img src=\"" + this.pageContext.getServletContext().getContextPath() + "/images/manager/limit/front.gif\" onclick=\"gotoFrontPage()\"/></a>");
		} else {
			outcontent.append("<a href =\"javascript:void(0);\"><img src=\"" + this.pageContext.getServletContext().getContextPath() + "/images/manager/limit/front_disable.gif\"\"/></a>");
		}
		outcontent.append("&nbsp;&nbsp;&nbsp;");
		if (this.pageIndex != this.totalPage && this.totalRows > this.pageSize) {
			outcontent.append("<a href =\"javascript:void(0);\"><img src=\"" + this.pageContext.getServletContext().getContextPath() + "/images/manager/limit/next.gif\" onclick=\"gotoNextPage()\"/></a>");
		} else {
			outcontent.append("<a href =\"javascript:void(0);\"><img src=\"" + this.pageContext.getServletContext().getContextPath() + "/images/manager/limit/next_disable.gif\"\"/></a>");
		}
		outcontent.append("&nbsp;&nbsp;&nbsp;");
		if (this.pageIndex != this.totalPage && this.totalRows > this.pageSize) {
			outcontent.append("<a href =\"javascript:void(0);\"><img src=\"" + this.pageContext.getServletContext().getContextPath() + "/images/manager/limit/last.gif\" onclick=\"gotoLastPage()\"/></a>");
		} else {
			outcontent.append("<a href =\"javascript:void(0);\"><img src=\"" + this.pageContext.getServletContext().getContextPath() + "/images/manager/limit/last_disable.gif\"\"/></a>");
		}
		outcontent.append("</td>");
		outcontent.append("<td style=\"width:500px;font-size: 12px;text-align: center;\">");
		outcontent.append("总共" + this.totalRows + "条");
		outcontent.append("&nbsp;&nbsp;");
		outcontent.append("第" + (this.totalRows > 0 ? (this.startRow + 1) : 0) + "条 - 第" + this.endRow + "条");
		outcontent.append("&nbsp;&nbsp;");
		outcontent.append("第" + this.pageIndex + "/" + this.totalPage + "页");
		outcontent.append("</td>");
		outcontent.append("<td style=\"width:150px;font-size: 12px;text-align: center;\">");
		outcontent.append("每页显示 <input type=\"text\" id=\"pageSize\" name=\"pageSize\" value=\"" + this.pageSize + "\" maxlength=\"4\" style=\"width:30px;height:17px;text-align:center\"/> 行");
		outcontent.append("<a href=\"javascript:resetPagesize();\" style=\"text-decoration:none;\">设置</a>");
		outcontent.append("<input type=\"hidden\" id=\"actionPath\" name=\"actionPath\" value=\"" + this.actionPath + "\"/>");
		outcontent.append("<input type=\"hidden\" id=\"totalRows\" name=\"totalRows\" value=\"" + this.totalRows + "\"/>");
		outcontent.append("<input type=\"hidden\" id=\"totalPage\" name=\"totalPage\" value=\"" + this.totalPage + "\"/>");
		outcontent.append("<input type=\"hidden\" id=\"pageIndex\" name=\"pageIndex\" value=\"" + this.pageIndex + "\"/>");
		outcontent.append("<input type=\"hidden\" id=\"startRow\" name=\"startRow\" value=\"" + this.startRow + "\"/>");
		outcontent.append("<input type=\"hidden\" id=\"endRow\" name=\"endRow\" value=\"" + this.pageIndex + "\"/>");
		outcontent.append("</td>");
		outcontent.append("<td></td>");
		outcontent.append("</tr>");
		outcontent.append("</table>");
		outcontent.append("<script type=\"text/javascript\">");
		outcontent.append("function gotoFirstPage(){");
		outcontent.append("document." + this.formId + ".pageIndex.value=\"1\";");
		outcontent.append("document." + this.formId + ".startRow.value=\"0\";");
		outcontent.append("document." + this.formId + ".endRow.value=\"" + this.pageSize + "\";");
		outcontent.append("submitPageLimit(0);");
		outcontent.append("}");
		outcontent.append("function gotoFrontPage(){");
		outcontent.append("document." + this.formId + ".pageIndex.value=\"" + (this.pageIndex - 1) + "\";");
		outcontent.append("document." + this.formId + ".startRow.value=\"" + (this.pageIndex - 1 == 1 ? 0 : (this.pageIndex - 2) * this.pageSize) + "\";");
		outcontent.append("document." + this.formId + ".endRow.value=\"" + (this.pageIndex * this.pageSize) + "\";");
		outcontent.append("submitPageLimit(0);");
		outcontent.append("}");
		outcontent.append("function gotoNextPage(){");
		outcontent.append("document." + this.formId + ".pageIndex.value=\"" + (this.pageIndex + 1) + "\";");
		outcontent.append("document." + this.formId + ".startRow.value=\"" + (this.pageIndex * this.pageSize) + "\";");
		outcontent.append("document." + this.formId + ".endRow.value=\"" + ((this.pageIndex + 1) * this.pageSize) + "\";");
		outcontent.append("submitPageLimit(0);");
		outcontent.append("}");
		outcontent.append("function gotoLastPage(){");
		outcontent.append("document." + this.formId + ".pageIndex.value=\"" + this.totalPage + "\";");
		outcontent.append("document." + this.formId + ".startRow.value=\"" + ((this.totalPage - 1) * this.pageSize) + "\";");
		outcontent.append("document." + this.formId + ".endRow.value=\"" + this.totalRows + "\";");
		outcontent.append("submitPageLimit(0);");
		outcontent.append("}");

		outcontent.append("function resetPagesize(){");
		outcontent.append("var tempsize = document.getElementById(\"pageSize\").value;");
		outcontent.append("if(tempsize ==" + this.pageSize + "){return ;}");
		outcontent.append("var temprows = document.getElementById(\"totalRows\").value;");
		outcontent.append("var temppage = Math.floor(temprows/tempsize);");
		outcontent.append("if(temprows % tempsize!=0){");
		outcontent.append("temppage=temppage+1;");
		outcontent.append("}");
		outcontent.append("document.getElementById(\"totalPage\").value=temppage;");
		outcontent.append("document.getElementById(\"pageIndex\").value=1;");
		outcontent.append("document.getElementById(\"startRow\").value=0;");
		outcontent.append("document.getElementById(\"endRow\").value=tempsize;");
		outcontent.append("submitPageLimit(0);");
		outcontent.append("}");
		outcontent.append("function submitPageLimit(changecode){");
		outcontent.append("document." + this.formId + ".action=\"" + this.actionPath + "\";");
		outcontent.append("if(changecode==1){");
		outcontent.append("document." + this.formId + ".actionPath.value=\"\";");
		outcontent.append("document." + this.formId + ".totalRows.value=\"0\";");
		outcontent.append("document." + this.formId + ".totalPage.value=\"0\";");
		outcontent.append("document." + this.formId + ".pageIndex.value=\"0\";");
		outcontent.append("document." + this.formId + ".startRow.value=\"0\";");
		outcontent.append("document." + this.formId + ".endRow.value=\"0\";");
		outcontent.append("}");
		outcontent.append("document." + this.formId + ".submit();");
		outcontent.append("}");
		outcontent.append(" </script>");
		try {
			pageContext.getOut().println(outcontent.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Tag.EVAL_PAGE;
	}

	public String toString() {
		StringBuilder json = new StringBuilder();
		json.append("{formId:" + this.formId);
		json.append(",actionPath:" + this.actionPath);
		json.append(",totalRows:" + this.totalRows);
		json.append(",pageSize:" + this.pageSize);
		json.append(",totalPage:" + this.totalPage);
		json.append(",pageIndex:" + this.pageIndex);
		json.append(",startRow:" + this.startRow);
		json.append(",endRow:" + this.endRow);
		json.append("}");
		return json.toString();
	}

}
