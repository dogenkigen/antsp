def euc_2d(c1, c2)
Math.sqrt((c1[0] - c2[0])**2.0 + (c1[1] - c2[1])**2.0).round
end
def cost(permutation, cities)
distance =0 permutation.each_with_index do |c1, i|
    c2 = (i==permutation.size-1) ? permutation[0] : permutation[i+1]
    distance += euc_2d(cities[c1], cities[c2])
end
return distance end
def random_permutation(cities)
perm = Array.new(cities.size){|i| i} perm.each_index do |i|
    r = rand(perm.size-i) + i
    perm[r], perm[i] = perm[i], perm[r]
end
return perm end