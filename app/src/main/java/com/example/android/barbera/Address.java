package com.example.android.barbera;


import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
     String city;
     String lane;
     String road;
     String house;

    public Address(String city, String lane, String road, String house) {
        this.city = city;
        this.lane = lane;
        this.road = road;
        this.house = house;
    }
    @Override
    public String toString() {
        return "City    :  " + city + "\nLane  :  " + lane + "\nRoad  :  " + road + "\nHouse:  " + house;
    }
    protected Address(Parcel in) {
        city = in.readString();
        lane = in.readString();
        road = in.readString();
        house = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(lane);
        dest.writeString(road);
        dest.writeString(house);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
