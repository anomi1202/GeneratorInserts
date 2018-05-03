delete from <TABLE_1> where ID between {ID_START} and {ID_END};
delete from <TABLE_2> where ID between {ID_START} and {ID_END};
delete from <TABLE_3> where ID between {ID_START} and {ID_END};
commit;



// insert for <TABLE_2>
insert into <TABLE_2> (FIELD_1, FIELD_2 FIELD_3) values (-0000000, 'VALUE_2', 'VALUE_3');
commit;


// insert for <TABLE_3>
insert into <TABLE_3> (FIELD_1, FIELD_2 FIELD_3) values (-0000000, 'VALUE_2', 'VALUE_3');
commit;


// insert for <TABLE_1>
insert into <TABLE_1> (FIELD_1, FIELD_2 FIELD_3) values (-0000000, 'VALUE_2', 'VALUE_3');
commit;


