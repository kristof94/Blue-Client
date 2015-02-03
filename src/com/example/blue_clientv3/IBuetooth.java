package com.example.blue_clientv3;

public interface IBuetooth {

	void set_paired_device(String name);
	void create_scoket();
	Object get_socket();
	Object getAdapter();
	boolean get_ready();
	
}
