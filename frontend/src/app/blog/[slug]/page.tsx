export default async function DynamicPage({
  params,
}: {
  params: Promise<{slug: string}>;
}){
  const slug = (await params).slug;
  return <h1>Detail page {slug}</h1>
}