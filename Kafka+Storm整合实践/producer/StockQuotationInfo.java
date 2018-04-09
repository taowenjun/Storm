package cn.bit.tao.producer;

import java.io.Serializable;

/**
 *@author  tao wenjun
 *��Ʊ��Ϣ��
 */

public class StockQuotationInfo implements Serializable {

	/**  �汾��Ϣ  */
	private static final long serialVersionUID = 1L;

	/** ��Ʊ����  */
	private String stockCode;
	
	/** ��Ʊ���� */
	private String stockName;
	
	/** ����ʱ�� */
	private long tradeTime;
	
	/** ��������ʱ�� */
	private float preClosePrice;
	
	/** ���̼� */
	private float openPrice;
	
	/** ��ǰ�ۣ�����ʱ��Ϊ�������̼� */
	private float currentPrice;
	
	/** ������߼� */
	private float highPrice;
	
	/** ������ͼ� */
	private float lowPrice;

	/**
	 * @return the stockCode
	 */
	public String getStockCode() {
		return stockCode;
	}

	/**
	 * @param stockCode the stockCode to set
	 */
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	/**
	 * @return the stockName
	 */
	public String getStockName() {
		return stockName;
	}

	/**
	 * @param stockName the stockName to set
	 */
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	/**
	 * @return the tradeTime
	 */
	public long getTradeTime() {
		return tradeTime;
	}

	/**
	 * @param tradeTime the tradeTime to set
	 */
	public void setTradeTime(long tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**
	 * @return the preClosePrice
	 */
	public float getPreClosePrice() {
		return preClosePrice;
	}

	/**
	 * @param preClosePrice the preClosePrice to set
	 */
	public void setPreClosePrice(float preClosePrice) {
		this.preClosePrice = preClosePrice;
	}

	/**
	 * @return the openPrice
	 */
	public float getOpenPrice() {
		return openPrice;
	}

	/**
	 * @param openPrice the openPrice to set
	 */
	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}

	/**
	 * @return the currentPrice
	 */
	public float getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * @param currentPrice the currentPrice to set
	 */
	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}

	/**
	 * @return the highPrice
	 */
	public float getHighPrice() {
		return highPrice;
	}

	/**
	 * @param highPrice the highPrice to set
	 */
	public void setHighPrice(float highPrice) {
		this.highPrice = highPrice;
	}

	/**
	 * @return the lowPrice
	 */
	public float getLowPrice() {
		return lowPrice;
	}

	/**
	 * @param lowPrice the lowPrice to set
	 */
	public void setLowPrice(float lowPrice) {
		this.lowPrice = lowPrice;
	}
	
	@Override
	public String toString() {
		return this.stockCode+"|"+this.stockName+"|"+tradeTime+"|"+preClosePrice+
				"|"+openPrice+"|"+currentPrice+"|"+highPrice+"|"+lowPrice;
	}
}
