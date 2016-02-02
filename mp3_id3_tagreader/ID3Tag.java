package mp3;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class ID3Tag {

	String title;
	String artist;
	String album;
	int year;
	String comment;
	int genre;

	private ID3Tag() {
	}

	public static ID3Tag parse(byte[] bytes) {

		String title = new String(readXBytes(bytes, 3, 33)).trim();
		String artist = new String(readXBytes(bytes, 33, 63)).trim();
		String album = new String(readXBytes(bytes, 63, 93)).trim();
		byte[] year = readXBytes(bytes, 93, 97);
		String comment = new String(readXBytes(bytes, 97, 125)).trim();
		byte[] genre = readXBytes(bytes, 127, 128);

		ID3Tag tag = new ID3Tag();
		tag.setTitle(title);
		tag.setArtist(artist);
		tag.setAlbum(album);
		tag.setYear(year);
		tag.setComment(comment);
		tag.setGenre(genre);

		return tag;
	}

	private static byte[] readXBytes(byte[] byteArray, int fromPos, int toPos) {

		byte[] result = new byte[toPos - fromPos];
		for (int i = fromPos; i < toPos; i++) {
			result[i - fromPos] = byteArray[i];
		}
		return result;
	}

	public static byte[] tail(File file) {
		try {
			RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
			long fileLength = fileHandler.length() - 1;
			byte[] buffer = new byte[128];

			for (int i = 0; i < buffer.length; i++) {
				fileHandler.seek(fileLength - 127 + i);
				buffer[i] = fileHandler.readByte();
			}
			fileHandler.close();
			return buffer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(byte[] year2) {
		String year3 = new String(year2).trim();
		int year4 = new Integer(year3);
		this.year = year4;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getGenre() {
		return genre;
	}

	public void setGenre(byte[] genre2) {
		if (genre2 == null) {
			String genre3 = "0";
			int genre4 = new Integer(genre3);
			this.genre = genre4;
		} else {
			int genre4 = ByteBuffer.wrap(genre2).get();
			this.genre = genre4;
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Title:").append(title).append('\n');
		sb.append("Artist:").append(artist).append('\n');
		sb.append("Album:").append(album).append('\n');
		sb.append("Year:").append(year).append('\n');
		sb.append("Comment:").append(comment).append('\n');
		sb.append("Genre:").append(genre).append('\n');
		return sb.toString();
	}

	public static void main(String[] args) {

		byte[] id3Bytes = tail(new File("//write here your .mp3 file's path//"));
		ID3Tag tag = ID3Tag.parse(id3Bytes);
		System.out.println(tag.toString());
	}
}
