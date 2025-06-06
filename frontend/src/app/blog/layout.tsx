import React, { ReactNode } from "react";

export default function BlogLayout ({
    children
}:{
    children: React.ReactNode
}
){
    type Props = {children : ReactNode};

    const Component = ({children}:Props) =><div>{children}</div>


    return <Component>{children}</Component>
  }