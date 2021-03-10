package jh.hadoop.mapreduce.sample.input;

import com.google.common.collect.ComparisonChain;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ChatLogWritable implements WritableComparable<ChatLogWritable> {

    private String bjId;
    private String bjName;
    private String bTitle;
    private long bStartTime;
    private int allViewer;
    private int pcViewer;
    private int mobileViewer;
    private int relayViewer;
    private int totalViewer;
    private String userTitles;
    private String userId;
    private int loginIndex;
    private String userNick;
    private boolean isMobile;
    private long userflag;
    private String grade;
    private String chatText;
    private long chatNow;
    private String version;
    private int upCount;

    public ChatLogWritable(Text text) {
        String[] tokens = text.toString().split("\t");
        bjId = tokens[0];
        bjName = tokens[1];
        bTitle = tokens[2];
        bStartTime = Long.parseLong(tokens[3]);
        allViewer = Integer.parseInt(tokens[4]);
        pcViewer = Integer.parseInt(tokens[5]);
        mobileViewer = Integer.parseInt(tokens[6]);
        relayViewer = Integer.parseInt(tokens[7]);
        totalViewer = Integer.parseInt(tokens[8]);
        userTitles = tokens[9];
        userId = tokens[10];
        loginIndex = Integer.parseInt(tokens[11]);
        userNick = tokens[12];
        isMobile = Boolean.parseBoolean(tokens[13]);
        userflag = Long.parseLong(tokens[14]);
        grade = tokens[15];
        chatText = tokens[16];
        chatNow = Long.parseLong(tokens[17]);
        version = tokens[18];
        upCount = Integer.parseInt(tokens[19]);
    }

    public String getBjId() {
        return bjId;
    }

    public String getBjName() {
        return bjName;
    }

    public String getbTitle() {
        return bTitle;
    }

    public long getbStartTime() {
        return bStartTime;
    }

    public int getAllViewer() {
        return allViewer;
    }

    public int getPcViewer() {
        return pcViewer;
    }

    public int getMobileViewer() {
        return mobileViewer;
    }

    public int getRelayViewer() {
        return relayViewer;
    }

    public int getTotalViewer() {
        return totalViewer;
    }

    public String getUserTitles() {
        return userTitles;
    }

    public String getUserId() {
        return userId;
    }

    public int getLoginIndex() {
        return loginIndex;
    }

    public String getUserNick() {
        return userNick;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public long getUserflag() {
        return userflag;
    }

    public String getGrade() {
        return grade;
    }

    public String getChatText() {
        return chatText;
    }

    public long getChatNow() {
        return chatNow;
    }

    public String getVersion() {
        return version;
    }

    public int getUpCount() {
        return upCount;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        WritableUtils.writeString(out, bjId);
        WritableUtils.writeString(out, bjName);
        WritableUtils.writeString(out, bTitle);
        out.writeLong(bStartTime);
        out.writeInt(allViewer);
        out.writeInt(pcViewer);
        out.writeInt(mobileViewer);
        out.writeInt(relayViewer);
        out.writeInt(totalViewer);
        WritableUtils.writeString(out, userTitles);
        WritableUtils.writeString(out, userId);
        out.writeInt(loginIndex);
        WritableUtils.writeString(out, userNick);
        out.writeBoolean(isMobile);
        out.writeLong(userflag);
        WritableUtils.writeString(out, grade);
        WritableUtils.writeString(out, chatText);
        out.writeLong(chatNow);
        WritableUtils.writeString(out, version);
        out.writeInt(upCount);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        bjId = WritableUtils.readString(in);
        bjName = WritableUtils.readString(in);
        bTitle = WritableUtils.readString(in);
        bStartTime = in.readLong();
        allViewer = in.readInt();
        pcViewer = in.readInt();
        mobileViewer = in.readInt();
        relayViewer = in.readInt();
        totalViewer = in.readInt();
        userTitles = WritableUtils.readString(in);
        userId = WritableUtils.readString(in);
        loginIndex = in.readInt();
        userNick = WritableUtils.readString(in);
        isMobile = in.readBoolean();
        userflag = in.readLong();
        grade = WritableUtils.readString(in);
        chatText = WritableUtils.readString(in);
        chatNow = in.readLong();
        version = WritableUtils.readString(in);
        upCount = in.readInt();
    }

    @Override
    public int compareTo(ChatLogWritable o) {
        return ComparisonChain.start().
                compare(bjId, o.bjId).
                compare(bStartTime, o.bStartTime).
                compare(userId, o.userId).
                compare(chatNow, o.chatNow).result();
    }
}
