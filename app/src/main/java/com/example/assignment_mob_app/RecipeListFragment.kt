package com.example.assignment_mob_app

class RecipeListFragment : Fragment(), RecipeAdapter.RecipeItemListener {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadSampleRecipes()
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(this)
        binding.recipesRecyclerView.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun loadSampleRecipes() {
        // Sample data - replace with your own recipes and images
        val sampleRecipes = listOf(
            Recipe(1, "Pasta Carbonara", "Classic Italian pasta dish", R.drawable.sample_pasta),
            Recipe(2, "Caesar Salad", "Fresh and crispy salad", R.drawable.sample_salad),
            Recipe(3, "Chocolate Cake", "Rich chocolate dessert", R.drawable.sample_cake)
        )
        recipeAdapter.updateRecipes(sampleRecipes)
    }

    override fun onRecipeClick(recipe: Recipe) {
        Log.d("RecipeList", "Recipe clicked: ${recipe.id}")
        Toast.makeText(context, "Recipe clicked: ${recipe.title}", Toast.LENGTH_SHORT).show()
        // TODO: Navigate to recipe details
    }

    override fun onShareClick(recipe: Recipe) {
        Log.d("RecipeList", "Share clicked for recipe: ${recipe.id}")
        Toast.makeText(context, "Sharing recipe: ${recipe.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onLikeClick(recipe: Recipe) {
        Log.d("RecipeList", "Like clicked for recipe: ${recipe.id}")
        Toast.makeText(
            context,
            "${if (recipe.isLiked) "Liked" else "Unliked"}: ${recipe.title}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = RecipeListFragment()
    }
}