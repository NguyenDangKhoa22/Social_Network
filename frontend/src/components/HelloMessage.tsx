// components/HelloMessage.txs
"use client";
class Student{
  fullName: String;
  constructor(
    public firstName:string,
    public middleInitial:string,
    public lastName:string,
  ){
    this.fullName = this.firstName + " " + this.middleInitial + " " + this.lastName;
  }

}
interface Person{
  fullname:Student
}
function greeter(person:Person){
  return "Hello, "+ person.fullname.fullName;
}

let use = new Student("Nguyen","Dang","Khoa");


export default function HelloMessage(){
    return (
      <div>
         <p className="text-xl text-white bg-blue-500 px-4 py-2 rounded shadow-md">
          Hello{greeter({fullname: use})}
        </p>
      </div>
     
    );
}