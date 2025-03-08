import { notFound } from "next/navigation";

interface BlogPostProps {
  params: { slug: string };
}

export default function BlogPost({ params }: BlogPostProps) {
  
  const slug = params.slug;

  if (!slug) return notFound();

  const posts: { [key: string]: { title: string; content: string } } = {
    "hello-world": { title: "Hello World", content: "Chào mừng bạn đến với blog!" },
    "nextjs-tutorial": { title: "Next.js", content: "Next.js rất mạnh mẽ và dễ dùng." },
  };

  const post = posts[slug];

  if (!post) return notFound();

  return (
    <div>
      <h1>{post.title}</h1>
      <p>{post.content}</p>
    </div>
  );
}