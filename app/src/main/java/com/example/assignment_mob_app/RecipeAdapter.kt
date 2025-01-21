package com.example.assignment_mob_app

class RecipeAdapter(private val listener: RecipeItemListener) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private val recipes = mutableListOf<Recipe>()

    interface RecipeItemListener {
        fun onRecipeClick(recipe: Recipe)
        fun onShareClick(recipe: Recipe)
        fun onLikeClick(recipe: Recipe)
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.apply {
                recipeTitle.text = recipe.title
                recipeImage.setImageResource(recipe.imageResId)
                likeButton.isSelected = recipe.isLiked

                // Set click listeners
                root.setOnClickListener { listener.onRecipeClick(recipe) }
                shareButton.setOnClickListener { listener.onShareClick(recipe) }
                likeButton.setOnClickListener {
                    recipe.isLiked = !recipe.isLiked
                    likeButton.isSelected = recipe.isLiked
                    listener.onLikeClick(recipe)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount() = recipes.size

    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes.clear()
        recipes.addAll(newRecipes)
        notifyDataSetChanged()
    }
}
