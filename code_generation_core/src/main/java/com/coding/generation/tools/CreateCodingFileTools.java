package com.coding.generation.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.coding.jdbc.exception.SYHCException;

/**
 * 文件生成工具
 * 
 * @Copyright MacChen
 * @Project CodeGenerationTool
 * @Author MacChen
 * @timer 2017-12-01
 * @Version 1.0.0
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
@Repository
public class CreateCodingFileTools {

	// 文件生成路径
	@Value("${coding_create_dir}")
	private String coding_create_dir;

	public void writeFile(List<String> rowList, String databaseName, String tableName, String filename) throws SYHCException {
		String filepath = coding_create_dir + databaseName + "/" + tableName + "/";
		File directory = new File(filepath);
		if (directory.isDirectory() == false) {
			directory.mkdirs();
		}
		String targetName = filepath + filename;
		File file = new File(targetName);
		if (file.exists()) {
			file.delete();
		}
		// System.out.println(targetName);
		file = new File(targetName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file, true);
			for (String row : rowList) {
				row = row + "\n";
				out.write(row.getBytes("utf-8"));
			}
			return;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		throw new SYHCException(SYHCException.OPERATION_CODE_EXECUTE_FAILER, "写文件失败【" + filename + "】");
	}

	public void writeFile(String contents, String databaseName, String tableName, String filename) throws SYHCException {
		String filepath = coding_create_dir + databaseName + "/" + tableName + "/";
		File directory = new File(filepath);
		if (directory.isDirectory() == false) {
			directory.mkdirs();
		}
		String targetName = filepath + filename;
		File file = new File(targetName);
		if (file.exists()) {
			file.delete();
		}
		// System.out.println(targetName);
		file = new File(targetName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file, true);
			out.write(contents.getBytes("utf-8"));
			return;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		throw new SYHCException(SYHCException.OPERATION_CODE_EXECUTE_FAILER, "写文件失败【" + filename + "】");
	}

}
