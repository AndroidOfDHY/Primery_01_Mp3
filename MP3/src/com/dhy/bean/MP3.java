/**
 * @author 何广军
 *
 * 2015-9-4
 */
package com.dhy.bean;


/**
 * MP3实体类，用于保存从XML文件中读取的数据
 */
public class MP3 {
	
	private int id; //标识id
	private String mp3name; // 歌名
	private long mp3size; // 歌曲长度
	private String mp3singer; // 歌手名
	private String lrcname; // 歌词名
	private long lrcsize; // 歌词长度
	
	public MP3() {
		super();
	}

	public MP3(int id, String mp3name, long mp3size, String mp3singer,
			String lrcname, long lrcsize) {
		super();
		this.id = id;
		this.mp3name = mp3name;
		this.mp3size = mp3size;
		this.mp3singer = mp3singer;
		this.lrcname = lrcname;
		this.lrcsize = lrcsize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMp3name() {
		return mp3name;
	}

	public void setMp3name(String mp3name) {
		this.mp3name = mp3name;
	}

	public long getMp3size() {
		return mp3size;
	}

	public void setMp3size(long mp3size) {
		this.mp3size = mp3size;
	}

	public String getMp3singer() {
		return mp3singer;
	}

	public void setMp3singer(String mp3singer) {
		this.mp3singer = mp3singer;
	}

	public String getLrcname() {
		return lrcname;
	}

	public void setLrcname(String lrcname) {
		this.lrcname = lrcname;
	}

	public long getLrcsize() {
		return lrcsize;
	}

	public void setLrcsize(long lrcsize) {
		this.lrcsize = lrcsize;
	}

	@Override
	public String toString() {
		return "MP3 [id=" + id + ", mp3name=" + mp3name + ", mp3size="
				+ mp3size + ", mp3singer=" + mp3singer + ", lrcname=" + lrcname
				+ ", lrcsize=" + lrcsize + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((mp3name == null) ? 0 : mp3name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MP3 other = (MP3) obj;
		if (id != other.id)
			return false;
		if (mp3name == null) {
			if (other.mp3name != null)
				return false;
		} else if (!mp3name.equals(other.mp3name))
			return false;
		return true;
	}
	
	
	

}
