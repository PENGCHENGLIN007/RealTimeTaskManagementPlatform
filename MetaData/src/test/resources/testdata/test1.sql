insert into user_info (name,EMAIL,PASSWORD)
values('张可','123@qq.com','123456');

select * from column_info;
select * from source_info;
select * from sink_info;
select * from task_info;
select * from task_info;

select * from user_info;

insert into task_info(name ,USER_ID,`sql` )
value ('计算总金额-一分钟窗口',1,'select sum(amount) from source1 group by TUMBLE(proctime, INTERVAL ''1'' MINUTE);');

update  task_info set USER_ID=1;

delete from task_info;
 delete from source_info;
 delete from sink_info;
